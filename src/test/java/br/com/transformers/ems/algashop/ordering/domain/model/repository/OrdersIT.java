package br.com.transformers.ems.algashop.ordering.domain.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.provider.OrdersPersistenceProvider;

@DataJpaTest
@Import({OrdersPersistenceProvider.class, OrderPersistenceEntityAssembler.class, OrderPersistenceEntityDisassembler.class})
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

        Optional<Order> possibleOrder = orders.ofId(order.id());

        Assertions.assertThat(possibleOrder).isPresent();

        var savedOrder = possibleOrder.get();

        Assertions.assertThat(savedOrder).satisfies(
            s -> Assertions.assertThat(s.id()).isEqualTo(order.id()),
            s -> Assertions.assertThat(s.customerId()).isEqualTo(s.customerId()),
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

}