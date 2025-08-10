package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;

public class OrderItemPersistenceTestDataBuilder {

    private OrderItemPersistenceTestDataBuilder() {

    }

    public static Set<OrderItemPersistenceEntity> anOrderItem(OrderPersistenceEntity order) {
        OrderItemPersistenceEntity oi = OrderItemPersistenceEntity.builder()
                    .id(IdGenerator.generateTSID().toLong())
                    .orderId(order.getId())
                    .price(BigDecimal.ONE)
                    .productId(IdGenerator.generateUUID())
                    .productName("pn")
                    .quantity(Long.MIN_VALUE)
                    .totalAmmount(BigDecimal.TWO)
                    .order(order)
                .build();;

        HashSet<OrderItemPersistenceEntity> items = new HashSet<>();
        items.add(oi);
        
        return items;
    }

}
