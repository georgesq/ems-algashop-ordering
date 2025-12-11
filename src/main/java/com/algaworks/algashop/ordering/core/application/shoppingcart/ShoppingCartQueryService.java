package com.algaworks.algashop.ordering.core.application.shoppingcart;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ForQueryingShoppingCarts;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ShoppingCartOutput;
import com.algaworks.algashop.ordering.core.ports.out.shoppingcart.ForObtainingShoppingCarts;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingCartQueryService implements ForQueryingShoppingCarts {

    private final ForObtainingShoppingCarts obtainingShoppingCarts;

    @Override
    public ShoppingCartOutput findById(UUID shoppingCartId) {

        return obtainingShoppingCarts.findById(shoppingCartId);

    }

    @Override
    public ShoppingCartOutput findByCustomerId(UUID customerId) {

        return obtainingShoppingCarts.findByCustomerId(customerId);
        
    }

}
