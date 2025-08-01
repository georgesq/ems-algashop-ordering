package br.com.transformers.ems.algashop.ordering.domain.entity;

import java.util.List;

public enum OrderStatus {

    DRAFT,
    PLACED(DRAFT),
    PAID(PLACED),
    READY(PAID),
    CANCELED(DRAFT, PLACED, PAID, READY, PAID);

    private List<OrderStatus> previousStates;

    OrderStatus(OrderStatus... previousStates) {
        this.previousStates = List.of(previousStates);
    }

    public boolean canChangeTo(OrderStatus newStatus) {

        return newStatus.previousStates.contains(this);

    }

    public boolean canotChangeTo(OrderStatus newStatus) {

        return !canChangeTo(newStatus);

    }
}
