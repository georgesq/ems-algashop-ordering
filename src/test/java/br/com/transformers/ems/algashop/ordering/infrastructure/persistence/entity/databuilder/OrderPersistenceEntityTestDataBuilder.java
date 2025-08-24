package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;

public class OrderPersistenceEntityTestDataBuilder {

    private boolean withItems;
    private Set<OrderItemPersistenceEntity> items;

    private OrderPersistenceEntityTestDataBuilder() {

    }

    public static OrderPersistenceEntityTestDataBuilder draft() {
        return new OrderPersistenceEntityTestDataBuilder();
    }

    public OrderPersistenceEntity build() {
        OrderPersistenceEntity orderPersistenceEntity = anOPE();

        if (this.items == null || this.items.isEmpty()) {
            HashSet<OrderItemPersistenceEntity> oiep = new HashSet<>(2);

            if (withItems) {
                oiep.add(OrderItemPersistenceTestDataBuilder.anOrderItem(orderPersistenceEntity));
                oiep.add(OrderItemPersistenceTestDataBuilder.anOrderItem(orderPersistenceEntity));
            }

            orderPersistenceEntity.setItems(oiep);
        } else {
            orderPersistenceEntity.setItems(items);
        }


        return orderPersistenceEntity;

    }

    private OrderPersistenceEntity anOPE() {
        return OrderPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .customer(CustomerPersistenceEntityTestDataBuilder.aCustomerPersistenceEntity().build())
                .totalAmount(new BigDecimal("99.99"))
                .totalItems(5L)
                .createdAt(OffsetDateTime.now().minusDays(2))
                .placedAt(OffsetDateTime.now().minusDays(1))
                .paidAt(OffsetDateTime.now())
                .paymentMethod("CREDIT_CARD")
                .status(OrderStatus.PLACED.toString())
                .billing(BillingEmbeddableTestDataBuilder.aBilling())
                .shipping(ShippingEmbeddableTestDataBuilder.aShipping())
                .build();

    }

    public OrderPersistenceEntityTestDataBuilder withItems(boolean withItems) {
        this.withItems = withItems;

        return this;
    }

    public OrderPersistenceEntityTestDataBuilder items(Set<OrderItemPersistenceEntity> items) {
        this.items = items;

        return this;
    }
}
