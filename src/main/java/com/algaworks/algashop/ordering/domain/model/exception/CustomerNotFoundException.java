package com.algaworks.algashop.ordering.domain.model.exception;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;

public class CustomerNotFoundException extends DomainException {

    public CustomerNotFoundException(CustomerId customerId) {
        super((String.format("Customer with ID %s does not exist.", customerId.value())));
    }

}
