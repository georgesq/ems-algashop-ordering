package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.AddressTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Address;

class AddressEmbeddableAssemblerTest {

    @Test
    void fromDomain_shouldMapDomainToEmbeddable() {
        
        Address domain = AddressTestDataBuilder.anAddress().build();

        Assertions.assertThat(AddressEmbeddableAssembler.fromDomain(domain)).satisfies(embeddable -> {
            assertThat(embeddable.getStreet()).isEqualTo(domain.street().toString());
            assertThat(embeddable.getNumber().toString()).isEqualTo(domain.number().toString());
            assertThat(embeddable.getComplement()).isEqualTo(domain.complement().toString());
            assertThat(embeddable.getNeighborhood()).isEqualTo(domain.neighborhood().toString());
            assertThat(embeddable.getCity()).isEqualTo(domain.city().toString());
            assertThat(embeddable.getState()).isEqualTo(domain.state().toString());
            assertThat(embeddable.getZipCode()).isEqualTo(domain.zipCode().toString());
        });

    }

}