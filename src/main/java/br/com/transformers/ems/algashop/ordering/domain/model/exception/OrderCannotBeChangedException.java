package br.com.transformers.ems.algashop.ordering.domain.model.exception;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;

public class OrderCannotBeChangedException extends DomainException{

    public OrderCannotBeChangedException(OrderId id, OrderStatus status, OrderStatus newStatus) {
        super(String.format("Order cannot be change: id %s, from %s to %s", id, status, newStatus));
    }

}
