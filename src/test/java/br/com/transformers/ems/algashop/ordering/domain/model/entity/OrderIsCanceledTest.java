package br.com.transformers.ems.algashop.ordering.domain.model.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;

public class OrderIsCanceledTest {
    
    @Test
    void testIsCanceledReturnsTrueWhenOrderStatusIsCanceled() {
        var order = OrderTestDataBuilder.anOrder()
            .status(OrderStatus.CANCELED)
            .build();

        Assertions.assertThat(order.isCanceled()).isTrue();
    }

    @Test
    void testIsCanceledReturnsFalseWhenOrderStatusIsNotCanceled() {
        var order = OrderTestDataBuilder.anOrder()
            .status(OrderStatus.PLACED)
            .build();

        Assertions.assertThat(order.isCanceled()).isFalse();
    }


}
