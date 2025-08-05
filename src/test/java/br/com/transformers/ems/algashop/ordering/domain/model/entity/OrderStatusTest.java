package br.com.transformers.ems.algashop.ordering.domain.model.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;

public class OrderStatusTest {

    @Test
    void testCanChangeTo() {

        Assertions.assertThat(OrderStatus.PAID.canChangeTo(OrderStatus.READY)).isTrue();
        Assertions.assertThat(OrderStatus.PAID.canChangeTo(OrderStatus.CANCELED)).isTrue();

    }

    @Test
    void testCanotChangeTo() {

        Assertions.assertThat(OrderStatus.READY.canChangeTo(OrderStatus.DRAFT)).isFalse();

    }
}
