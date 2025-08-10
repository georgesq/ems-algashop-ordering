package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.ShippingEmbeddable;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder.ShippingEmbeddableTestDataBuilder;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;





class ShippingEmbeddableTestDataBuilderTest {

    @Test
    void aShipping_shouldReturnShippingEmbeddableWithExpectedValues() {
        ShippingEmbeddable shipping = ShippingEmbeddableTestDataBuilder.aShipping();

        assertThat(shipping).isNotNull();
        assertThat(shipping.getCost()).isEqualByComparingTo(new BigDecimal("10.00"));
        assertThat(shipping.getExpectedDate()).isEqualTo(LocalDate.now().plusDays(5));
        assertThat(shipping.getRecipient()).isNotNull();
        assertThat(shipping.getAddress()).isNotNull();
    }
}