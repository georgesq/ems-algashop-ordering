package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;

public class QuantityTest {

    @Test
    void testGivenNegativeValueThenThrowsIllegalArgumentException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Quantity(-1));
    }

    @Test
    void testGivenNullValueThenThrowsIllegalArgumentException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Quantity(null));
    }
}
