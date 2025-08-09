package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.ShippingTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Shipping;

class ShippingEmbeddableAssemblerTest {

    @Test
    void fromDomain_shouldMapDomainToEmbeddable() {
        
        Shipping domain = ShippingTestDataBuilder.aShipping().build();

        Assertions.assertThat(ShippingEmbeddableAssembler.fromDomain(domain)).satisfies(embeddable -> {
            assertThat(embeddable.getCost()).isEqualTo(domain.cost().value());
            assertThat(embeddable.getExpectedDate()).isEqualTo(domain.expectedDate());
            assertThat(embeddable.getRecipient()).isNotNull();
            assertThat(embeddable.getAddress()).isNotNull();
        });

    }

}