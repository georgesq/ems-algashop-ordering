package com.algaworks.algashop.ordering.domain.model.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCartTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.exception.CustomerAlreadyHaveShoppingCartException;
import com.algaworks.algashop.ordering.domain.model.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.repository.ShoppingCarts;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.Quantity;

@SpringBootTest
public class ShoppingServiceIT {

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private Customers customers;

    @Autowired
    private ShoppingCarts shoppingCarts;

    @Test
    void givenCorrectData_thenCreateShoppingCart() {
        
        // Arrange
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        var customerId = customer.id();
        
        this.customers.add(customer);

        // Act
        var shoppingCart = this.shoppingService.startShopping(customerId);

        // Assert
        Assertions.assertThat(shoppingCart).satisfies(sc -> {
            Assertions.assertThat(sc).isNotNull();
            Assertions.assertThat(sc.id()).isNotNull();
            Assertions.assertThat(sc.customerId()).isEqualTo(customerId);
            Assertions.assertThat(sc.totalItems()).isEqualTo(new Quantity(0));
            Assertions.assertThat(sc.totalAmount()).isEqualTo(Money.ZERO);
        });

    }

    @Test
    void givenInvalidClient_thenCustomerNotFoundException() {
        
        // Arrange
        var customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

        // Assert
        Assertions.assertThatExceptionOfType(CustomerNotFoundException.class)
            .isThrownBy(() -> this.shoppingService.startShopping(customerId));

    }

    @Test
    void givenCustomerWithActiveShoppingCart_thenCustomerAlreadyHaveShoppingCartException() {
        
        // Arrange
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        var customerId = customer.id();
        this.customers.add(customer);

        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().customerId(customerId).build();
        this.shoppingCarts.add(shoppingCart);

        // Assert
        Assertions.assertThatExceptionOfType(CustomerAlreadyHaveShoppingCartException.class)
            .isThrownBy(() -> this.shoppingService.startShopping(customerId));

    }

}
