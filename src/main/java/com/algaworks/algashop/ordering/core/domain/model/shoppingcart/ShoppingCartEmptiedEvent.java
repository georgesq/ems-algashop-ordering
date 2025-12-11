package com.algaworks.algashop.ordering.core.domain.model.shoppingcart;

import java.time.OffsetDateTime;

import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerId;

public record ShoppingCartEmptiedEvent(
    ShoppingCartId shoppingCartId,
    CustomerId customerId,
    OffsetDateTime emptiedAt
) {}
