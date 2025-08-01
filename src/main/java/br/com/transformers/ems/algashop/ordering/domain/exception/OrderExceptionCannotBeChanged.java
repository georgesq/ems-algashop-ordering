package br.com.transformers.ems.algashop.ordering.domain.exception;

import br.com.transformers.ems.algashop.ordering.domain.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.OrderId;

public class OrderExceptionCannotBeChanged extends DomainException{

    public OrderExceptionCannotBeChanged(OrderId id, OrderStatus status, OrderStatus newStatus) {
        super(String.format("Order cannot be change: id %s, from %s to %s", id, status, newStatus));
    }

}
