package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity.OrderPersistenceEntityBuilder;

public class OrderPersistenceEntityTestDataBuilder {

    private OrderPersistenceEntityTestDataBuilder() {
    }

    public static OrderPersistenceEntityBuilder existingOrder() {
        return OrderPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .customer(CustomerPersistenceEntityTestDataBuilder.aCustomer().build())
                .totalItems(3l)
                .totalAmount(new BigDecimal(1250))
                .status("DRAFT")
                .paymentMethod("CREDIT_CARD")
                .placedAt(OffsetDateTime.now())
                .items(Set.of(
                        existingItem().build(),
                        existingItemAlt().build()
                ));
    }

    public static OrderItemPersistenceEntity.OrderItemPersistenceEntityBuilder existingItem() {
        return OrderItemPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .price(new BigDecimal(500))
                .quantity(2l)
                .totalAmount(new BigDecimal(1000))
                .productName("Notebook")
                .productId(IdGenerator.generateUUID());
    }

    public static OrderItemPersistenceEntity.OrderItemPersistenceEntityBuilder existingItemAlt() {
        return OrderItemPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .price(new BigDecimal(250))
                .quantity(1l)
                .totalAmount(new BigDecimal(250))
                .productName("Mouse pad")
                .productId(IdGenerator.generateUUID());
    }
}