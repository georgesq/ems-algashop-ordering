package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.OrderPersistenceEntityAssembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder.OrderItemPersistenceTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder.OrderPersistenceEntityTestDataBuilder;

class OrderPersistenceEntityAssemblerTest {

    private OrderPersistenceEntityAssembler assembler;

    @BeforeEach
    void setUp() {
        assembler = new OrderPersistenceEntityAssembler();
    }

    @Test
    void fromDomain_shouldMapAllFields() {

        Order order = OrderTestDataBuilder.anOrder().build();

        OrderPersistenceEntity entity = assembler.fromDomain(order);

        assertThat(entity.getId()).isEqualTo(order.id().value().toLong());
        assertThat(entity.getCustomerId()).isEqualTo(order.customerId().value());
        assertThat(entity.getTotalAmount()).isEqualTo(order.totalAmount().value());
        assertThat(entity.getTotalItems()).isEqualTo(order.totalItems().value());
        assertThat(entity.getCreatedAt()).isEqualTo(order.createdAt());
        assertThat(entity.getPlacedAt()).isEqualTo(order.placedAt());
        assertThat(entity.getPaidAt()).isEqualTo(order.paidAt());
        assertThat(entity.getCanceledAt()).isEqualTo(order.canceledAt());
        assertThat(entity.getReadAt()).isEqualTo(order.readAt());
        assertThat(entity.getStatus()).isEqualTo(order.status().toString());
        assertThat(entity.getPaymentMethod()).isEqualTo(order.paymentMethod().toString());

        Assertions.assertThat(entity).satisfies(e -> {
            assertThat(e.getBilling()).isNotNull();
            assertThat(e.getShipping()).isNotNull();
            assertThat(e.getVersion()).isEqualTo(order.version());
        });

    }

    @Test
    public void givenOrderWithNoItems_thenRemoveAllPersistenceEntityItems() {
 
        Order order = OrderTestDataBuilder.anOrder().withItems(false).build();
        OrderPersistenceEntity ope = OrderPersistenceEntityTestDataBuilder.existingOrder();

        ope.addItem(OrderItemPersistenceTestDataBuilder.anOrderItem(ope));

        Assertions.assertThat(order.items()).isEmpty();
        Assertions.assertThat(ope.getItems()).isNotEmpty();

        assembler.merge(ope, order);

        Assertions.assertThat(ope.getItems()).isEmpty();

    }

    @Test
    public void givenOrderWithItems_thenAddToPersistenceEntity() {

        Order order = OrderTestDataBuilder.anOrder().build();
        OrderPersistenceEntity ope = OrderPersistenceEntityTestDataBuilder.existingOrder();

        ope.addItem(OrderItemPersistenceTestDataBuilder.anOrderItem(ope));

        Assertions.assertThat(order.items()).isNotEmpty();
        Assertions.assertThat(ope.getItems()).isNotEmpty();

        assembler.merge(ope, order);

        Assertions.assertThat(ope.getItems().size()).isEqualTo(order.items().size());
        
    }
}