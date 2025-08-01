package br.com.transformers.ems.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductNameTest {

    @Test
    void testGivenInvalidValueThenThrowsIllegalArgumentException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new ProductName(""));
    }
}
