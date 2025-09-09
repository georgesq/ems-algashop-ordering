package com.algaworks.algashop.ordering.domain.model.order.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.order.entity.BillingTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.order.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.order.entity.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.order.entity.ShippingTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.order.shipping.valueobject.Billing;
import com.algaworks.algashop.ordering.domain.model.order.shipping.valueobject.Shipping;
import com.algaworks.algashop.ordering.domain.model.product.entity.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.entity.ShoppingCartTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.exception.ShoppingCartCantProceedToCheckoutException;

@SpringBootTest
public class CheckoutServiceIT {

    @Autowired
    private CheckoutService checkoutService;

    @Test
    void givenValidShoppingCart_whenCheckout_thenReturnsOrder() {

        // Arrange
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        Billing billing = BillingTestDataBuilder.aBilling().build();
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        PaymentMethod paymentMethod= PaymentMethod.CREDIT_CARD;

        // Act
        var order = this.checkoutService.checkout(shoppingCart, billing, shipping, paymentMethod);

        // Assert
        Assertions.assertThat(order).satisfies(o -> {
            Assertions.assertThat(o).isNotNull();
            Assertions.assertThat(o.id()).isNotNull();
            Assertions.assertThat(o.customerId()).isEqualTo(shoppingCart.customerId());
            Assertions.assertThat(o.billing()).isEqualTo(billing);
            Assertions.assertThat(o.shipping()).isEqualTo(shipping);
            Assertions.assertThat(o.paymentMethod()).isEqualTo(paymentMethod);
            Assertions.assertThat(o.items()).hasSize(2);
            Assertions.assertThat(o.status()).isEqualTo(OrderStatus.PLACED);
            Assertions.assertThat(shoppingCart.isEmpty()).isTrue();
        });

    }

    @Test
    void givenShoppingCartWithUnavailableItem_whenCheckout_thenShoppingCartCantProceedToCheckoutException() {

        // Arrange
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        Product unAvailableProduct = ProductTestDataBuilder.aProduct().name(new ProductName("toUnavailablePrd")).build();
        shoppingCart.addItem(unAvailableProduct, new Quantity(1));

        unAvailableProduct = ProductTestDataBuilder.aProduct().id(unAvailableProduct.id()).inStock(false).name(new ProductName("toUnavailablePrd")).build();
        shoppingCart.refreshItem(unAvailableProduct);

        Billing billing = BillingTestDataBuilder.aBilling().build();
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        PaymentMethod paymentMethod= PaymentMethod.CREDIT_CARD;

        // Act
        Assertions.assertThatExceptionOfType(ShoppingCartCantProceedToCheckoutException.class)
            .isThrownBy(() -> this.checkoutService.checkout(shoppingCart, billing, shipping, paymentMethod));

        // Assert
        Assertions.assertThat(shoppingCart).satisfies(o -> {
            Assertions.assertThat(shoppingCart.isEmpty()).isFalse();
        });

    }

}
