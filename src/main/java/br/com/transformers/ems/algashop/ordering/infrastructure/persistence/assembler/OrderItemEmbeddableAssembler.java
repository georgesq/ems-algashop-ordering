package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import java.util.HashSet;
import java.util.Set;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;

public class OrderItemEmbeddableAssembler {

    public static Set<OrderItemPersistenceEntity> fromDomain(Order order,
            OrderPersistenceEntity orderPersistenceEntity) {

        if (order.items().isEmpty()) {
            orderPersistenceEntity.getItems().clear();

            return new HashSet<>();
        }

        return orderPersistenceEntity.getItems();
    }

}
