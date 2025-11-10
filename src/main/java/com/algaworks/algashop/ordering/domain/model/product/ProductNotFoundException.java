package com.algaworks.algashop.ordering.domain.model.product;

import com.algaworks.algashop.ordering.domain.model.DomainEntityNotFoundException;

public class ProductNotFoundException extends DomainEntityNotFoundException {

    public ProductNotFoundException(ProductId productId) {

        super(String.format("Product with ID %s not found", productId.value()));
        
    }
    
}
