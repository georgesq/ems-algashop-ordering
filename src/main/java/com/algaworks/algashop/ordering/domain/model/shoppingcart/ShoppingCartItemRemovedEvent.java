package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import java.time.OffsetDateTime;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.product.ProductId;

public record ShoppingCartItemRemovedEvent(

    ShoppingCartId shoppingCartId,
    CustomerId customerId,
    ProductId productId,
    OffsetDateTime removedAt

) {
    
}
