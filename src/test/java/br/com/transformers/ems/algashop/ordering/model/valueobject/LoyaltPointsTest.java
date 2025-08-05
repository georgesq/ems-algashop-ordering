package br.com.transformers.ems.algashop.ordering.model.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.model.exception.LoyaltyPointException;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.LoyaltPoints;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LoyaltPointsTest {

    @Test
    void shouldNew() {
        LoyaltPoints loyaltPoints = new LoyaltPoints(10);

        Assertions.assertThat(loyaltPoints.value()).isEqualTo(10);
    }

    @Test
    void shouldAdd() {
        LoyaltPoints loyaltPoints = new LoyaltPoints(10);

        loyaltPoints = loyaltPoints.add(5);

        Assertions.assertThat(loyaltPoints.value()).isEqualTo(15);
    }

    @Test
    void shouldAddInvalidValue() {
        LoyaltPoints loyaltPoints = new LoyaltPoints(10);

        Assertions.assertThatExceptionOfType(LoyaltyPointException.class).isThrownBy(() -> loyaltPoints.add(0));
    }

    @Test
    void shouldNewInvalidValue() {
        Assertions.assertThatExceptionOfType(LoyaltyPointException.class).isThrownBy(() -> new LoyaltPoints(-10));
    }
}