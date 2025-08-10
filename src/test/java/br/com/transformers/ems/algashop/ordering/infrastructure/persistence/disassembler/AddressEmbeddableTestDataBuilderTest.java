package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.AddressEmbeddable;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder.AddressEmbeddableTestDataBuilder;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AddressEmbeddableTestDataBuilderTest {

    @Test
    void anAddress_shouldReturnAddressEmbeddableWithExpectedFields() {
        AddressEmbeddable address = AddressEmbeddableTestDataBuilder.anAddress();

        assertThat(address).isNotNull();
        assertThat(address.getStreet()).isEqualTo("123 Main St");
        assertThat(address.getComplement()).isEqualTo("Apt 4B");
        assertThat(address.getNumber()).isEqualTo(123);
        assertThat(address.getNeighborhood()).isEqualTo("Downtown");
        assertThat(address.getCity()).isEqualTo("Springfield");
        assertThat(address.getState()).isEqualTo("IL");
        assertThat(address.getZipCode()).isEqualTo("62701");
    }
}