package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity.OrderPersistenceEntityBuilder;

public class OrderPersistenceEntityTestDataBuilder {

    private OrderPersistenceEntityTestDataBuilder() {

    }
    
    public static OrderPersistenceEntityBuilder existingOrder() {
        return OrderPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .totalAmount(new BigDecimal("99.99"))
                .totalItems(5L)
                .createdAt(OffsetDateTime.now().minusDays(2))
                .placedAt(OffsetDateTime.now().minusDays(1))
                .paidAt(OffsetDateTime.now())
                .paymentMethod("CREDIT_CARD")
                .status(OrderStatus.PLACED.toString())
                .billing(BillingEmbeddableTestDataBuilder.aBilling())
                .shipping(ShippingEmbeddableTestDataBuilder.aShipping())
        ;
    }
}
