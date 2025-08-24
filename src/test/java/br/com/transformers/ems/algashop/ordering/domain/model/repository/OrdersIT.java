package br.com.transformers.ems.algashop.ordering.domain.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;
import br.com.transformers.ems.algashop.ordering.infrastructure.config.JpaConfig;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.provider.OrdersPersistenceProvider;

@DataJpaTest
@Import({
    OrdersPersistenceProvider.class, 
    OrderPersistenceEntityAssembler.class, 
    OrderPersistenceEntityDisassembler.class,
    JpaConfig.class
})
class OrdersIT {

    private Orders orders;

    @Autowired
    public OrdersIT(Orders orders) {
        this.orders = orders;
    }

    @Test
    @DisplayName("should save and retrieve an order")
    void saveAndFindById() {

        Order order = OrderTestDataBuilder.anOrder().build();

        orders.add(order);

        var savedOrder = orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(savedOrder).satisfies(
            s -> Assertions.assertThat(s.id()).isEqualTo(order.id()),
            s -> Assertions.assertThat(s.customer()).isEqualTo(s.customer()),
            s -> Assertions.assertThat(s.totalAmount()).isEqualTo(order.totalAmount()),
            s -> Assertions.assertThat(s.totalItems()).isEqualTo(order.totalItems()),
            s -> Assertions.assertThat(s.status()).isEqualTo(order.status()),
            s -> Assertions.assertThat(s.placedAt()).isEqualTo(order.placedAt()),
            s -> Assertions.assertThat(s.paidAt()).isEqualTo(order.paidAt()),
            s -> Assertions.assertThat(s.readAt()).isEqualTo(order.readAt())
        );

    }

    @Test
    @DisplayName("should update an existing order")
    void updateOrder() {

        Order order = OrderTestDataBuilder.anOrder()
            .status(OrderStatus.PLACED)
        .build();

        orders.add(order);
        order = orders.ofId(order.id()).orElseThrow();

        order.markAsPaid();

        orders.add(order);
        order = orders.ofId(order.id()).orElseThrow();

        System.out.println(order);

        Assertions.assertThat(order).satisfies(
            s -> Assertions.assertThat(s.isPaid()).isTrue()
        );

    }

    @Test
    @DisplayName("should concurrent update then error")
    void shouldConcurrentUpdateThenThrow() {

        Order order = OrderTestDataBuilder.anOrder()
            .status(OrderStatus.PLACED)
        .build();

        orders.add(order);

        var savedOrder = orders.ofId(order.id()).orElseThrow();
        var savedOrder2 = orders.ofId(order.id()).orElseThrow();

        savedOrder.markAsPaid();
        orders.add(savedOrder);
        savedOrder2.cancel();

        Assertions.assertThatExceptionOfType(ObjectOptimisticLockingFailureException.class)
            .isThrownBy(() -> orders.add(savedOrder2));

        savedOrder = orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(savedOrder).satisfies(
            s -> Assertions.assertThat(s.isCanceled()).isFalse(),
            s -> Assertions.assertThat(s.isPaid()).isTrue()
        );
    }

    @Test
    void shouldCountCorrect() {

        Assertions.assertThat(orders.count()).isZero();

        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();

        orders.add(order);

        Assertions.assertThat(orders).satisfies(
            o -> Assertions.assertThat(o.count()).isEqualTo(1L)
        );

    }

    @Test
    void shouldExists() {

        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();
        orders.add(order);

        Assertions.assertThat(orders.exists(order.id())).isTrue();
        Assertions.assertThat(orders.exists(new OrderId())).isFalse();
        
    }
}