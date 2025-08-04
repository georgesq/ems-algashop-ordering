package br.com.transformers.ems.algashop.ordering.domain.model.entity;

import br.com.transformers.ems.algashop.ordering.domain.model.exception.OrderItemNoFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.exception.OrderCannotBeChangedException;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderItemId;

public class RemoveOrderItemTest {

    @Test
    void testGivenDraftOrderWhenRemoveItemThenItemRemovedAndTotalsUpdated() {
        var order = OrderTestDataBuilder.anOrder().build();
        var initialTotalItems = order.totalItems();
        var initialTotalAmount = order.totalAmount();

        OrderItem orderItem = order.items().iterator().next();
        var orderItemId = orderItem.id();

        order.removeItem(orderItemId);

        Assertions.assertThat(order.items().stream().anyMatch(i -> i.id().equals(orderItemId))).isFalse();
        Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(initialTotalItems.value() - orderItem.quantity().value()));
        Assertions.assertThat(order.totalAmount()).isEqualTo(new Money(initialTotalAmount.value().subtract(orderItem.totalAmmount().value())));
    }

    @Test
    void testGivenNonDraftOrderWhenRemoveItemThenExceptionThrown() {
        var order = OrderTestDataBuilder.anOrder().build();
        order.place();
        var orderItemId = order.items().iterator().next().id();

        Assertions.assertThatExceptionOfType(OrderCannotBeChangedException.class)
            .isThrownBy(() -> order.removeItem(orderItemId));
    }

    @Test
    void testGivenNullOrderItemIdWhenRemoveItemThenExceptionThrown() {
        var order = OrderTestDataBuilder.anOrder().build();

        Assertions.assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> order.removeItem(null));
    }

    @Test
    void testGivenNonExistingOrderItemIdWhenRemoveItemThenExceptionThrown() {
        var order = OrderTestDataBuilder.anOrder().build();
        var nonExistingId = new OrderItemId();

        Assertions.assertThatExceptionOfType(OrderItemNoFoundException.class)
            .isThrownBy(() -> order.removeItem(nonExistingId));
    }
    

}
