package br.com.transformers.ems.algashop.ordering.domain.model.exception;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;

public class OrderItemNoFoundException extends DomainException{

    public OrderItemNoFoundException(OrderId id) {
        super(String.format("Order %s, item not found", id));
    }

}
