package com.algaworks.algashop.ordering.application.order.management;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.domain.model.order.OrderId;
import com.algaworks.algashop.ordering.domain.model.order.OrderNotFoundException;
import com.algaworks.algashop.ordering.domain.model.order.Orders;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderManagementApplicationService {

    private final Orders orders;

	@Transactional
    public void cancel(Long rawOrderId) {

        Objects.requireNonNull(rawOrderId);

        var order = this.orders.ofId(new OrderId(rawOrderId)).orElseThrow(() -> new OrderNotFoundException());

        order.cancel();

        this.orders.add(order);

    }

	@Transactional
    public void markAsPaid(Long rawOrderId) {

        Objects.requireNonNull(rawOrderId);

        var order = this.orders.ofId(new OrderId(rawOrderId)).orElseThrow(() -> new OrderNotFoundException());

        order.markAsPaid();

        this.orders.add(order);

    }

	@Transactional
    public void markAsReady(Long rawOrderId) {

        Objects.requireNonNull(rawOrderId);

        var order = this.orders.ofId(new OrderId(rawOrderId)).orElseThrow(() -> new OrderNotFoundException());

        order.markAsReady();

        this.orders.add(order);

    }

}
