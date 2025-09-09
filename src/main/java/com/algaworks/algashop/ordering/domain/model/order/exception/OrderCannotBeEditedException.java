package com.algaworks.algashop.ordering.domain.model.order.exception;

import com.algaworks.algashop.ordering.domain.model.DomainException;
import com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages;
import com.algaworks.algashop.ordering.domain.model.order.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.order.valueobject.OrderId;

public class OrderCannotBeEditedException extends DomainException {

	public OrderCannotBeEditedException(OrderId id, OrderStatus status) {
		super(String.format(ErrorMessages.ERROR_ORDER_CANNOT_BE_EDITED, id, status));
	}
}