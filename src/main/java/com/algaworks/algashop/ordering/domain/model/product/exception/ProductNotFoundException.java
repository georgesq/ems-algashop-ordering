package com.algaworks.algashop.ordering.domain.model.product.exception;

import java.util.UUID;

import com.algaworks.algashop.ordering.domain.model.DomainException;

public class ProductNotFoundException extends DomainException {

    public ProductNotFoundException(UUID id) {
        super(String.format( "Product not found %s ", id));
    }

}
