package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.PaymentMethod;
import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;

class OrderPersistenceEntityDisassemblerTest {

    private final OrderPersistenceEntityDisassembler disassembler = new OrderPersistenceEntityDisassembler();

    @Test
    void toDomainEntity_shouldMapAllFieldsCorrectly() {
        // Arrange
        Long id = IdGenerator.generateTSID().toLong();
        UUID customerId = IdGenerator.generateUUID();
        BigDecimal totalAmount = new BigDecimal("99.99");
        Integer totalItems = 5;
        OffsetDateTime createdAt = OffsetDateTime.now().minusDays(2);
        OffsetDateTime placedAt = OffsetDateTime.now().minusDays(1);
        OffsetDateTime paidAt = OffsetDateTime.now();
        OffsetDateTime canceledAt = null;
        OffsetDateTime readAt = OffsetDateTime.now();
        String status = OrderStatus.PLACED.toString();
        String paymentMethod = PaymentMethod.CREDIT_CARD.toString();

        OrderPersistenceEntity entity = new OrderPersistenceEntity();
        
        entity.setId(id);
        entity.setCustomerId(customerId);
        entity.setTotalAmount(totalAmount);
        entity.setTotalItems(totalItems);
        entity.setCreatedAt(createdAt);
        entity.setPlacedAt(placedAt);
        entity.setPaidAt(paidAt);
        entity.setCanceledAt(canceledAt);
        entity.setReadAt(readAt);
        entity.setStatus(status);
        entity.setPaymentMethod(paymentMethod);

        // Act
        Order order = disassembler.toDomainEntity(entity);

        // Assert
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order.id()).isEqualTo(new OrderId(id));
        Assertions.assertThat(order.customerId()).isEqualTo(new CustomerId(customerId));
        Assertions.assertThat(order.totalAmount()).isEqualTo(new Money(totalAmount));
        Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(totalItems));
        Assertions.assertThat(order.createdAt()).isEqualTo(createdAt);
        Assertions.assertThat(order.placedAt()).isEqualTo(placedAt);
        Assertions.assertThat(order.paidAt()).isEqualTo(paidAt);
        Assertions.assertThat(order.canceledAt()).isNull();
        Assertions.assertThat(order.readAt()).isEqualTo(readAt);
        Assertions.assertThat(order.status()).isEqualTo(OrderStatus.PLACED);
        Assertions.assertThat(order.paymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
    }

}

