package br.com.transformers.ems.algashop.ordering.model.entity.databuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Shipping;

public class ShippingTestDataBuilder {

    private ShippingTestDataBuilder() {

    }

    public static Shipping.ShippingBuilder aShipping() {

        return Shipping.builder()
            .cost(new Money(BigDecimal.valueOf(10)))
            .expectedDate(LocalDate.now())
            .recipient(RecipientTestDataBuilder.aRecipient().build())
            ;

    }

}
