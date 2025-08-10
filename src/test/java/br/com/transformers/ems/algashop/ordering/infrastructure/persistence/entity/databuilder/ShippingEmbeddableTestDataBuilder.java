package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.ShippingEmbeddable;

public class ShippingEmbeddableTestDataBuilder {

    public static ShippingEmbeddable aShipping() {
        return ShippingEmbeddable.builder()
                .cost(new BigDecimal("10.00"))
                .expectedDate(LocalDate.now().plusDays(5))
                .recipient(RecipientEmbeddableTestDataBuilder.aRecipient())
                .address(AddressEmbeddableTestDataBuilder.anAddress())
            .build();
    }

}
