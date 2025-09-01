package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.RecipientEmbeddable;

class RecipientEmbeddableTestDataBuilderTest {

    @Test
    void shouldBuildRecipientEmbeddableWithExpectedValues() {
        RecipientEmbeddable recipient = RecipientEmbeddableTestDataBuilder.aRecipient();

        assertThat(recipient).isNotNull();
        assertThat(recipient.getFirstName()).isEqualTo("Jane");
        assertThat(recipient.getLastName()).isEqualTo("Doe");
        assertThat(recipient.getDocument()).isEqualTo("123-456-7890");
        assertThat(recipient.getPhone()).isEqualTo("123-456-7890");
    }
}