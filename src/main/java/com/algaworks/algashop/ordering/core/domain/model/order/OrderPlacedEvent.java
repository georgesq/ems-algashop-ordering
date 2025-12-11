package com.algaworks.algashop.ordering.core.domain.model.order;

import java.time.OffsetDateTime;

import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerId;

public record OrderPlacedEvent(OrderId orderId, CustomerId customerId, OffsetDateTime placedAt) {
}