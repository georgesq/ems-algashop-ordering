package com.algaworks.algashop.ordering.core.ports.out.shoppingcart;

import java.util.UUID;

import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ShoppingCartOutput;

public interface ForObtainingShoppingCarts {
    ShoppingCartOutput findById(UUID shoppingCartId);
    ShoppingCartOutput findByCustomerId(UUID customerId);
}
