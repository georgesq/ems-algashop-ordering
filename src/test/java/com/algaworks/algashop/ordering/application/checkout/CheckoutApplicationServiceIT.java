package com.algaworks.algashop.ordering.application.checkout;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.application.shoppingcart.management.CheckoutInput;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.Customers;
import com.algaworks.algashop.ordering.domain.model.order.OrderId;
import com.algaworks.algashop.ordering.domain.model.order.Orders;
import com.algaworks.algashop.ordering.domain.model.order.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.order.shipping.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.product.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartCantProceedToCheckoutException;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartId;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartNotFoundException;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCarts;

@SpringBootTest
@Transactional
public class CheckoutApplicationServiceIT {

    @Autowired
    private CheckoutApplicationService checkoutApplicationService;

    @Autowired
    private ShoppingCarts shoppingCarts;

    @Autowired
    private Orders orders;

    @Autowired
    private Customers customers;

    @MockitoBean
    private ShippingCostService shippingCostService;

    @BeforeEach
    void setup() {

        when(this.shippingCostService.calculate(Mockito.any(ShippingCostService.CalculationRequest.class)))
            .thenReturn(ShippingCostService.CalculationResult.builder()
                .cost(new Money("10"))
                .expectedDate(LocalDate.now().plusDays(10)).build()
        );

        if (!customers.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
            customers.add(CustomerTestDataBuilder.existingCustomer().build());
        }        

    }

    @Test
    void givenInvalidShoppingCartIdThenShoppingCartNotFoundException() {

        // arrange
        var input = CheckoutInput.builder()
                .shoppingCartId(new ShoppingCartId().value())
                .paymentMethod(PaymentMethod.CREDIT_CARD.toString())
                .shipping(ShippingInputTestDataBuilder.aShippingInput().build())
                .billing(BillingDataTestDataBuilder.aBillingData().build())
                .build();

        // act

        // assert
        Assertions.assertThatExceptionOfType(ShoppingCartNotFoundException.class)
            .isThrownBy(() -> this.checkoutApplicationService.checkout(input));

    }

    @Test
    void shouldCheckout() {

        // arrange
        Product product = ProductTestDataBuilder.aProduct().inStock(true).build();

        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();
        shoppingCart.addItem(product, new Quantity(1));
        shoppingCarts.add(shoppingCart);

        var input = CheckoutInput.builder()
                .shoppingCartId(shoppingCart.id().value())
                .paymentMethod(PaymentMethod.CREDIT_CARD.toString())
                .shipping(ShippingInputTestDataBuilder.aShippingInput().build())
                .billing(BillingDataTestDataBuilder.aBillingData().build())
                .build();

        // act
        var orderId = this.checkoutApplicationService.checkout(input);

        // assert
        var checkouted = this.orders.ofId(new OrderId(orderId));

        Assertions.assertThat(checkouted).isNotEmpty();
        
    }

@Test
    void shouldThrowShoppingCartCantProceedToCheckoutExceptionWhenCartIsEmpty() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();
        shoppingCarts.add(shoppingCart);

        CheckoutInput input = CheckoutInputTestDataBuilder.aCheckoutInput()
                .shoppingCartId(shoppingCart.id().value())
                .build();

        Assertions.assertThatExceptionOfType(ShoppingCartCantProceedToCheckoutException.class)
                .isThrownBy(() -> this.checkoutApplicationService.checkout(input));
    }

    @Test
    void shouldThrowShoppingCartCantProceedToCheckoutExceptionWhenCartContainsUnavailableItems() {
        Product product = ProductTestDataBuilder.aProduct().inStock(true).build();
        Product unavailableProduct = ProductTestDataBuilder.aProduct().id(product.id()).inStock(false).build();

        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();
        shoppingCart.addItem(product, new Quantity(1));
        shoppingCart.refreshItem(unavailableProduct);
        shoppingCarts.add(shoppingCart);

        CheckoutInput input = CheckoutInputTestDataBuilder.aCheckoutInput()
                .shoppingCartId(shoppingCart.id().value())
                .build();

        Assertions.assertThatExceptionOfType(ShoppingCartCantProceedToCheckoutException.class)
                .isThrownBy(() -> this.checkoutApplicationService.checkout(input));
    }

}
