package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.AddressEmbeddable;

public class AddressEmbeddableTestDataBuilder {

    public static AddressEmbeddable anAddress() {
        return AddressEmbeddable.builder()
                .street("123 Main St")
                .complement("Apt 4B")
                .number(123)
                .neighborhood("Downtown")
                .city("Springfield")
                .state("IL")
                .zipCode("62701")
            .build();
    }

}
