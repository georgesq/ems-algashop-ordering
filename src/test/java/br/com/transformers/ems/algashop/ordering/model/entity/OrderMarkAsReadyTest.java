package br.com.transformers.ems.algashop.ordering.model.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.exception.OrderCannotBeChangedException;
import br.com.transformers.ems.algashop.ordering.model.entity.databuilder.OrderTestDataBuilder;

public class OrderMarkAsReadyTest {
    
    @Test
    void testGivenPlacedOrderWhenMarkAsReadyThenStatusReadyAndReadAtSet() {
        var order = OrderTestDataBuilder.anPaidOrder().build();

        order.markAsReady();

        Assertions.assertWith(order,
            (o) -> Assertions.assertThat(o.isReady()).isTrue(),
            (o) -> Assertions.assertThat(o.readAt()).isNotNull()
        );
    }

    @Test
    void testGivenDraftOrderWhenMarkAsReadyThenException() {
        var order = OrderTestDataBuilder.anOrder()
            .status(OrderStatus.DRAFT)
            .build();

        Assertions.assertThatExceptionOfType(OrderCannotBeChangedException.class)
            .isThrownBy(order::markAsReady);
    }

    @Test
    void testGivenPlacedOrderWhenMarkAsReadyThenException() {
        var order = OrderTestDataBuilder.anOrder()
            .status(OrderStatus.PLACED)
            .build();

        Assertions.assertThatExceptionOfType(OrderCannotBeChangedException.class)
            .isThrownBy(order::markAsReady);
    }

    @Test
    void testGivenReadyOrderWhenMarkAsReadyThenException() {
        var order = OrderTestDataBuilder.anOrder()
            .status(OrderStatus.READY)
            .build();

        Assertions.assertThatExceptionOfType(OrderCannotBeChangedException.class)
            .isThrownBy(order::markAsReady);
    }

    @Test
    void testGivenOrderWithStatusCannotChangeToReadyWhenMarkAsReadyThenException() {
        var order = OrderTestDataBuilder.anOrder()
            .status(OrderStatus.CANCELED)
            .build();

        Assertions.assertThatExceptionOfType(OrderCannotBeChangedException.class)
            .isThrownBy(order::markAsReady);
    }


}
