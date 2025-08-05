package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ProductName;

public class ProductNameTest {

    @Test
    void testGivenInvalidValueThenThrowsIllegalArgumentException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new ProductName(""));
    }
}
