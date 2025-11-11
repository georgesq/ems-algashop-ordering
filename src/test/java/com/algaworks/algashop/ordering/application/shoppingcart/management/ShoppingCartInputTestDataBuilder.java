package com.algaworks.algashop.ordering.application.shoppingcart.management;

import java.util.UUID;

import com.algaworks.algashop.ordering.presentation.shoppingcart.ShoppingCartInput;

public class ShoppingCartInputTestDataBuilder {

    public static ShoppingCartInput aShoppingCart(UUID customerId) {
        return ShoppingCartInput.builder()
                .customerId(customerId)
                .build();
    }

}
