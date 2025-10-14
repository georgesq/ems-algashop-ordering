package com.algaworks.algashop.ordering.application.shoppingcart.query;

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

import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.Customers;
import com.algaworks.algashop.ordering.domain.model.order.shipping.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.product.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartId;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartNotFoundException;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCarts;

@SpringBootTest
@Transactional
public class ShoppingCartQueryServiceIT {

    @Autowired
    private Customers customers;

    @Autowired
    private ShoppingCarts shoppingCarts;

    @Autowired
    private ShoppingCartQueryService service;

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
    void testFindById() {

        // arrange
        Product product = ProductTestDataBuilder.aProduct().inStock(true).build();
        
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();
        var id = shoppingCart.id().value();

        shoppingCart.addItem(product, new Quantity(1));
        shoppingCarts.add(shoppingCart);

        shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();
        shoppingCart.addItem(product, new Quantity(10));
        shoppingCarts.add(shoppingCart);

        // act
        var finded = this.service.findById(id);

        // asserts
        Assertions.assertThat(finded).isNotNull();
        Assertions.assertThat(finded.getId()).isEqualTo(id);

    }

    @Test
    void givenInvalidIdThenShoppingCartNotFoundException() {

        // arrange
        var id = new ShoppingCartId().value();

        // act
        
        // asserts
        Assertions.assertThatExceptionOfType(ShoppingCartNotFoundException.class).isThrownBy(() -> this.service.findById(id));

    }

    @Test
    void testFindByCustomerId() {

        // arrange
        Product product = ProductTestDataBuilder.aProduct().inStock(true).build();
        
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();

        shoppingCart.addItem(product, new Quantity(1));
        shoppingCarts.add(shoppingCart);

        CustomerId customerId = new CustomerId();
        customers.add(CustomerTestDataBuilder.existingCustomer().id(customerId).build());

        shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().customerId(customerId).withItems(false).build();
        shoppingCart.addItem(product, new Quantity(10));
        shoppingCarts.add(shoppingCart);

        // act
        var finded = this.service.findByCustomerId(customerId.value());

        // asserts
        Assertions.assertThat(finded).isNotNull();
        Assertions.assertThat(finded.getCustomerId()).isEqualTo(shoppingCart.customerId().value());

    }

    @Test
    void givenInvalidCustomerIdThenShoppingCartNotFound() {

        // arrange
        Product product = ProductTestDataBuilder.aProduct().inStock(true).build();
        
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();

        shoppingCart.addItem(product, new Quantity(1));
        shoppingCarts.add(shoppingCart);

        CustomerId customerId = new CustomerId();

        // act

        // asserts
        Assertions.assertThatExceptionOfType(ShoppingCartNotFoundException.class).isThrownBy(() -> this.service.findByCustomerId(customerId.value()));

    }

}
