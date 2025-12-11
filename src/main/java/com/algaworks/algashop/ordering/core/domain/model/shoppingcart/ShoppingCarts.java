package com.algaworks.algashop.ordering.core.domain.model.shoppingcart;

import java.util.Optional;

import com.algaworks.algashop.ordering.core.domain.model.RemoveCapableRepository;
import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerId;

public interface ShoppingCarts extends RemoveCapableRepository<ShoppingCart, ShoppingCartId> {
	Optional<ShoppingCart> ofCustomer(CustomerId customerId);
}