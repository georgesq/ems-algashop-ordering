package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.BillingTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Billing;

class BillingEmbeddableAssemblerTest {

    @Test
    void fromDomain_shouldMapDomainToEmbeddable() {
        
        Billing domain = BillingTestDataBuilder.aBilling().build();

        Assertions.assertThat(BillingEmbeddableAssembler.fromDomain(domain)).satisfies(embeddable -> {
            assertThat(embeddable.getAddress()).isNotNull();
            assertThat(embeddable.getDocument()).isEqualTo(domain.document().toString());
            assertThat(embeddable.getEmail()).isEqualTo(domain.email().toString());
            assertThat(embeddable.getFullName()).isEqualTo(domain.fullName().toString());
            assertThat(embeddable.getPhone()).isEqualTo(domain.phone().toString());
        });

    }

}