package br.com.transformers.ems.algashop.ordering.domain.exception;

import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.OrderId;

public class OrderCannotChangeItemException extends DomainException {

    private OrderCannotChangeItemException(String message) {
        super(message);
    }

    public static void noOrderItemId(OrderId id) {
        throw new OrderCannotChangeItemException(String.format("Order %s cannot change item, invalid OrderItemId", id));
    }

    public static Exception noQuantity(OrderId id) {
        throw new OrderCannotChangeItemException(String.format("Order %s cannot change item, invalid Quantity", id));
    }

}
