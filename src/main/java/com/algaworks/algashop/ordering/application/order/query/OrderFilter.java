package com.algaworks.algashop.ordering.application.order.query;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.domain.Sort;

import com.algaworks.algashop.ordering.application.utility.SortablePageFilter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderFilter extends SortablePageFilter<OrderFilter.SortType> {

    private String status;
    private String orderId;
    private UUID customerId;
    private OffsetDateTime placedAtFrom;
    private OffsetDateTime placedAtTo;
    private BigDecimal totalAmountFrom;
    private BigDecimal totalAmountTo;

    public OrderFilter(int size, int page) {
        super(size, page);
    }

    @Override
    public SortType getSortByPropertyOrDefault() {
        return getSortByProperty() == null ? SortType.PLACE_AT : getSortByProperty();
    }

    @Override
    public Sort.Direction getSortDirectionOrDefault() {
        return getSortDirection() == null ? Sort.Direction.ASC : getSortDirection();
    }

    @Getter
    @RequiredArgsConstructor
    public enum SortType {
        PLACE_AT("placedAt"),
        PAID_AT("paidAt"),
        CANCELED_AT("canceledAt"),
        READY_AT("readyAt"),
        STATUS("status");

        private final String propertyName;
    }
}