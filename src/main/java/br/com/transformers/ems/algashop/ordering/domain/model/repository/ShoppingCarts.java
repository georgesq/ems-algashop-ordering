package br.com.transformers.ems.algashop.ordering.domain.model.repository;

import java.util.Optional;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.ShoppingCart;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;

public interface ShoppingCarts extends Repository<ShoppingCart, ShoppingCartId> {

    Optional<ShoppingCart> ofCustomer(CustomerId customerId);

}
