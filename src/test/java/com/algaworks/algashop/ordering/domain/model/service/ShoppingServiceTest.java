package com.algaworks.algashop.ordering.domain.model.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCartTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.exception.CustomerAlreadyHaveShoppingCartException;
import com.algaworks.algashop.ordering.domain.model.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.repository.ShoppingCarts;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;

@ExtendWith(MockitoExtension.class)
public class ShoppingServiceTest {

    @Mock
    private Customers customers;

    @Mock
    private ShoppingCarts shoppingCarts;

    @InjectMocks
    private ShoppingService shoppingService;

    @Test
    void givenCorrectData_thenCreateShoppingCart() {
        
        // Arrange
        Mockito.when(customers.exists(Mockito.any(CustomerId.class)))
            .thenReturn(true);
        Mockito.when(shoppingCarts.ofCustomer(Mockito.any(CustomerId.class)))
            .thenReturn(Optional.empty());
        var customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

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
        Mockito.when(customers.exists(Mockito.any(CustomerId.class)))
            .thenReturn(false);
        var customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

        // Assert
        Assertions.assertThatExceptionOfType(CustomerNotFoundException.class)
            .isThrownBy(() -> this.shoppingService.startShopping(customerId));

    }

    @Test
    void givenCustomerWithActiveShoppingCart_thenCustomerAlreadyHaveShoppingCartException() {
        
        // Arrange
        Mockito.when(customers.exists(Mockito.any(CustomerId.class)))
            .thenReturn(true);
        Mockito.when(shoppingCarts.ofCustomer(Mockito.any()))
            .thenReturn(Optional.of(ShoppingCartTestDataBuilder.aShoppingCart().build()));
        var customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

        // Assert
        Assertions.assertThatExceptionOfType(CustomerAlreadyHaveShoppingCartException.class)
            .isThrownBy(() -> this.shoppingService.startShopping(customerId));

    }

}
