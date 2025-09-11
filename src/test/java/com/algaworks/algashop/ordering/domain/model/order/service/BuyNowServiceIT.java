package com.algaworks.algashop.ordering.domain.model.order.service;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.customer.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.order.entity.BillingTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.order.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.order.entity.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.order.entity.ShippingTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.order.shipping.valueobject.Billing;
import com.algaworks.algashop.ordering.domain.model.order.shipping.valueobject.Shipping;
import com.algaworks.algashop.ordering.domain.model.product.entity.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.product.exception.ProductOutOfStockException;

@SpringBootTest
public class BuyNowServiceIT {

    @Autowired
    private BuyNowService buyNowService;

    @Test
    void givenValidDataThenOrder() {

        // Arrange
        var product = ProductTestDataBuilder.aProduct().build();
        var quantity = new Quantity(10);
        var customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;
        Billing billing = BillingTestDataBuilder.aBilling().build();
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        PaymentMethod paymentMethod= PaymentMethod.CREDIT_CARD;
        
        // Act
        var order = this.buyNowService.buyNow(product, customerId, billing, shipping, quantity, paymentMethod);

        // Assert
        Assertions.assertThat(order).satisfies(o -> {
            Assertions.assertThat(o).isNotNull();
            Assertions.assertThat(o.id()).isNotNull();
            Assertions.assertThat(o.customerId()).isEqualTo(customerId);
            Assertions.assertThat(o.billing()).isEqualTo(billing);
            Assertions.assertThat(o.shipping()).isEqualTo(shipping);
            Assertions.assertThat(o.paymentMethod()).isEqualTo(paymentMethod);
            Assertions.assertThat(o.status()).isEqualTo(OrderStatus.PLACED);
            Assertions.assertThat(o.totalItems()).isEqualTo(new Quantity(10));
            Assertions.assertThat(o.totalAmount()).isEqualTo(new Money(product.price().value().multiply(BigDecimal.valueOf(quantity.value())).add(shipping.cost().value())));
        });

    }

    @Test
    void givenProductUnavailableThenThrowProductOutOfStockException() {

        var product = ProductTestDataBuilder.aProductUnavailable().build();
        var quantity = new Quantity(10);
        var customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;
        Billing billing = BillingTestDataBuilder.aBilling().build();
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        PaymentMethod paymentMethod= PaymentMethod.CREDIT_CARD;
        
        // Assert
        Assertions.assertThatExceptionOfType(ProductOutOfStockException.class)
            .isThrownBy(() -> this.buyNowService.buyNow(product, customerId, billing, shipping, quantity, paymentMethod));

    }

}
