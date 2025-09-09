package com.algaworks.algashop.ordering.domain.model.order.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algaworks.algashop.ordering.domain.model.commons.valueobject.ZipCode;
import com.algaworks.algashop.ordering.domain.model.order.shipping.service.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.order.shipping.service.ShippingCostService.CalculationRequest;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.service.OriginAddressService;

@SpringBootTest
class ShippingCostServiceIT {

    @Autowired
    private ShippingCostService shippingCostService;

    @Autowired
    private OriginAddressService originAddressService;

    @Test
    void shouldCalculate() {
        ZipCode origin = originAddressService.originAddress().zipCode();
        ZipCode destination = new ZipCode("12345");

        var calculate = shippingCostService
                .calculate(new CalculationRequest(origin, destination));

        Assertions.assertThat(calculate.cost()).isNotNull();
        Assertions.assertThat(calculate.expectedDate()).isNotNull();
    }
  
}