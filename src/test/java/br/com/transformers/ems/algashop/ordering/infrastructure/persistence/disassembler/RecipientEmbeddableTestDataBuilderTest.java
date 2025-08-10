package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.RecipientEmbeddable;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder.RecipientEmbeddableTestDataBuilder;

class RecipientEmbeddableTestDataBuilderTest {

    @Test
    void shouldBuildRecipientEmbeddableWithExpectedValues() {
        RecipientEmbeddable recipient = RecipientEmbeddableTestDataBuilder.aRecipient();

        assertThat(recipient).isNotNull();
        assertThat(recipient.getFullName()).isEqualTo("Jane Doe");
        assertThat(recipient.getDocument()).isEqualTo("123-456-7890");
        assertThat(recipient.getPhone()).isEqualTo("123-456-7890");
        assertThat(recipient.getAddress()).isNotNull();
        // Optionally, assert address fields if AddressEmbeddableTestDataBuilder.anAddress() returns known values
    }
}