package com.algaworks.algashop.ordering.domain.model.order.shipping.service;

import lombok.Builder;

import java.time.LocalDate;

import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.ZipCode;

public interface ShippingCostService {
    CalculationResult calculate(CalculationRequest request);

    @Builder
    record CalculationRequest(ZipCode origin, ZipCode destination) {}

    @Builder
    record CalculationResult(Money cost, LocalDate expectedDate) {}
}
