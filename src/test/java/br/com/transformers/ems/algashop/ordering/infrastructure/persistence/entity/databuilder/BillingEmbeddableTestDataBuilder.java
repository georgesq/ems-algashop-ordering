package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.BillingEmbeddable;

public class BillingEmbeddableTestDataBuilder {

    public static BillingEmbeddable aBilling() {
        return BillingEmbeddable.builder()
                .firstName("John")
                .lastName("Doe")
                .email("a@a.com")
                .address(AddressEmbeddableTestDataBuilder.anAddress())
                .document("12345678901")
            .build();
    }

}
