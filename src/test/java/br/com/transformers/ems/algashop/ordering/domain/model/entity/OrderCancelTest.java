package br.com.transformers.ems.algashop.ordering.domain.model.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.exception.OrderCannotBeChangedException;

public class OrderCancelTest {

    @Test
    void testGivenPlacedOrderWhenCancelThenStatusCanceledAndCanceledAtSet() {
        var order = OrderTestDataBuilder.anCanCancelOrder().build();

        order.cancel();

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isTrue(),
                (o) -> Assertions.assertThat(o.canceledAt()).isNotNull());

        order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.DRAFT)
                .build();

        order.cancel();

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isTrue(),
                (o) -> Assertions.assertThat(o.canceledAt()).isNotNull());
    }

    @Test
    void testGivenCanceledOrderWhenCancelThenExceptionThrown() {
        var order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.CANCELED)
                .build();

        Assertions.assertThatExceptionOfType(OrderCannotBeChangedException.class)
                .isThrownBy(order::cancel);
    }

}
