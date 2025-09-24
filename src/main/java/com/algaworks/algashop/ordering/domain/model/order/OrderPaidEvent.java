package com.algaworks.algashop.ordering.domain.model.order;

import java.time.OffsetDateTime;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;

public record OrderPaidEvent(
    OrderId orderId,
    CustomerId customerId,
    OffsetDateTime paidAt
) {
    
}
