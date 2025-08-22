package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder;

import java.math.BigDecimal;

import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;

public class OrderItemPersistenceTestDataBuilder {

    private OrderItemPersistenceTestDataBuilder() {

    }

    public static OrderItemPersistenceEntity anOrderItem(OrderPersistenceEntity order) {
        
        OrderItemPersistenceEntity oi = OrderItemPersistenceEntity.builder()
                    .id(IdGenerator.generateTSID().toLong())
                    .price(BigDecimal.ONE)
                    .productId(IdGenerator.generateUUID())
                    .productName("pn")
                    .quantity(Long.MAX_VALUE)
                    .totalAmmount(BigDecimal.TWO)
                    .order(order)
                .build();

        return oi;

    }

}
