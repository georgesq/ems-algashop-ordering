package com.algaworks.algashop.ordering.domain.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.Shipping;

public class ShippingTestDataBuilder {

    private ShippingTestDataBuilder() {

    }

    public static Shipping.ShippingBuilder aShipping() {

        return Shipping.builder()
            .cost(new Money(BigDecimal.valueOf(10)))
            .expectedDate(LocalDate.now())
            .address(AddressTestDataBuilder.anAddress().build())
            .recipient(RecipientTestDataBuilder.aRecipient().build())
            ;

    }

}
