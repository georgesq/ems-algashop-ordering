package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;

public class MoneyTest {


    @Test
    void testGivenEmptyValueThenThrowsIllegalArgumentException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Money(""));
    }

    @Test
    void testGivenNegativeValueThenThrowsIllegalArgumentException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Money(BigDecimal.valueOf(-1)));
    }

    @Test
    void testGivenValidValueWhenAddThenCorrectResult() {
        Assertions.assertThat(
            new Money(BigDecimal.valueOf(10).add(BigDecimal.valueOf(5)))
        ).isEqualTo(new Money(new BigDecimal(15)));

    }

    @Test
    void testGivenValidValueWhenDivideThenCorrectResult() {
        Assertions.assertThat(
            new Money(BigDecimal.valueOf(10)).divide(new Money(BigDecimal.valueOf(2)))
        ).isEqualTo(new Money(new BigDecimal(5)));
        
    }

    @Test
    void testGivenLessThanOneValueWhenMultiplyThenThrowsIllegalArgumentException() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Money(BigDecimal.valueOf(10)).multiply(new Quantity(0)));

    }

    @Test
    void testGivenValidValueWhenMultiplyThenCorrectResult() {

        Assertions.assertThat(
            new Money(BigDecimal.valueOf(10)).divide(new Money(BigDecimal.valueOf(2)))
        ).isEqualTo(new Money(new BigDecimal(5)));
        
    }
}

