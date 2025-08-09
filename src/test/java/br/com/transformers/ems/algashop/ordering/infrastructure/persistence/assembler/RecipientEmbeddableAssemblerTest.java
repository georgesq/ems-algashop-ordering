package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.RecipientTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Recipient;

class RecipientEmbeddableAssemblerTest {

    @Test
    void fromDomain_shouldMapDomainToEmbeddable() {
        
        Recipient domain = RecipientTestDataBuilder.aRecipient().build();

        Assertions.assertThat(RecipientEmbeddableAssembler.fromDomain(domain)).satisfies(embeddable -> {
            assertThat(embeddable.getDocument()).isEqualTo(domain.document().toString());
            assertThat(embeddable.getPhone()).isEqualTo(domain.phone().toString());
            assertThat(embeddable.getFullName()).isEqualTo(domain.fullName().toString());
            assertThat(embeddable.getAddress()).isNotNull();
        });

    }

}