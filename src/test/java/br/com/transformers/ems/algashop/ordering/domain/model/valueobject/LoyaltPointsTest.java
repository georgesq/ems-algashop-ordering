package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.exception.LoyaltyPointException;

class LoyaltPointsTest {

    @Test
    void shouldNew() {
        LoyaltyPoints loyaltPoints = new LoyaltyPoints(10);

        Assertions.assertThat(loyaltPoints.value()).isEqualTo(10);
    }

    @Test
    void shouldAdd() {
        LoyaltyPoints loyaltPoints = new LoyaltyPoints(10);

        loyaltPoints = loyaltPoints.add(5);

        Assertions.assertThat(loyaltPoints.value()).isEqualTo(15);
    }

    @Test
    void shouldNewInvalidValue() {
        Assertions.assertThatExceptionOfType(LoyaltyPointException.class).isThrownBy(() -> new LoyaltyPoints(-10));
    }
}