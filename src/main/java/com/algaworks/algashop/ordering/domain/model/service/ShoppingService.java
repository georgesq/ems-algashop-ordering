package com.algaworks.algashop.ordering.domain.model.service;

import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.exception.CustomerAlreadyHaveShoppingCartException;
import com.algaworks.algashop.ordering.domain.model.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.repository.ShoppingCarts;
import com.algaworks.algashop.ordering.domain.model.utility.DomainService;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class ShoppingService {

    private final Customers customers;

    private final ShoppingCarts shoppingCarts;

    public ShoppingCart startShopping(CustomerId customerId) {

        this.validateCustomer(customerId);
        
        ShoppingCart shoppingCart = ShoppingCart.startShopping(customerId);

        return shoppingCart;

    }

    private void validateCustomer(CustomerId customerId) {
        
        if (!this.customers.exists(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }

        this.shoppingCarts.ofCustomer(customerId)
            .ifPresent(c -> { 
                throw new CustomerAlreadyHaveShoppingCartException(customerId);
            }
        );
    }

}

