package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.DomainEntityNotFoundException;

public class CustomerNotFoundException extends DomainEntityNotFoundException {

    public CustomerNotFoundException(CustomerId customerId) {

        super(String.format("Customer with ID %s not found", customerId.value()));
        
    }
}
