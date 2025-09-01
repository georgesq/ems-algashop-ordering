package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.BillingEmbeddable;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder.BillingEmbeddableTestDataBuilder;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class BillingEmbeddableTestDataBuilderTest {

    @Test
    void shouldBuildBillingEmbeddableWithExpectedValues() {
        BillingEmbeddable billing = BillingEmbeddableTestDataBuilder.aBilling();

        assertThat(billing).isNotNull();
        assertThat(billing.getFirstName()).isEqualTo("John");
        assertThat(billing.getLastName()).isEqualTo("Doe");
        assertThat(billing.getEmail()).isEqualTo("a@a.com");
        assertThat(billing.getDocument()).isEqualTo("12345678901");
        assertThat(billing.getAddress()).isNotNull();
        
    }
}