package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.PaymentMethod;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder.OrderPersistenceEntityTestDataBuilder;

class OrderPersistenceEntityDisassemblerTest {

    private final OrderPersistenceEntityDisassembler disassembler = new OrderPersistenceEntityDisassembler();

    @Test
    void toDomainEntity_shouldMapAllFieldsCorrectly() {
        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.draft().withItems(true).build();
        
        // Act
        Order order = disassembler.toDomainEntity(entity);

        // Assert
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order.id()).isEqualTo(new OrderId(entity.getId()));
        Assertions.assertThat(order.customerId()).isEqualTo(new CustomerId(entity.getCustomerId()));
        Assertions.assertThat(order.totalAmount()).isEqualTo(new Money(entity.getTotalAmount()));
        Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(entity.getTotalItems()));
        Assertions.assertThat(order.createdAt()).isEqualTo(entity.getCreatedAt());
        Assertions.assertThat(order.placedAt()).isEqualTo(entity.getPlacedAt());
        Assertions.assertThat(order.paidAt()).isEqualTo(entity.getPaidAt());
        Assertions.assertThat(order.canceledAt()).isNull();
        Assertions.assertThat(order.readAt()).isEqualTo(entity.getReadAt());
        Assertions.assertThat(order.status()).isEqualTo(OrderStatus.PLACED);
        Assertions.assertThat(order.paymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);

        Assertions.assertThat(order).satisfies(o ->
            Assertions.assertThat(o.items()).isNotEmpty()
        );
    }

}

