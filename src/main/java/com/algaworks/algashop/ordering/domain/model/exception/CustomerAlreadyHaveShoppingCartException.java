package com.algaworks.algashop.ordering.domain.model.exception;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;

public class CustomerAlreadyHaveShoppingCartException extends DomainException {

    public CustomerAlreadyHaveShoppingCartException(CustomerId customerId) {
        super(String.format("Customer with ID %s already has an active shopping cart.", customerId.value()));
    }

}
