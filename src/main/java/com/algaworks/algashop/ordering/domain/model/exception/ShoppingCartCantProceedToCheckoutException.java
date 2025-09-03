package com.algaworks.algashop.ordering.domain.model.exception;

import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;

public class ShoppingCartCantProceedToCheckoutException extends DomainException {

    public ShoppingCartCantProceedToCheckoutException(ShoppingCart shoppingCart) {
        super("Shopping cart with ID %s can't proceed to checkout".formatted(shoppingCart.id().value()));
    }
    
}
