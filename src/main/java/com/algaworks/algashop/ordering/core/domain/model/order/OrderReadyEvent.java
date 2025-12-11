package com.algaworks.algashop.ordering.core.domain.model.order;

import java.time.OffsetDateTime;

import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerId;

public record OrderReadyEvent(OrderId orderId, CustomerId customerId, OffsetDateTime readyAt){
}