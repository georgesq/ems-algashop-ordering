package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderItem;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;

public class OrderItemEmbeddableAssembler {

    public static Set<OrderItemPersistenceEntity> fromDomain(Order order,
            OrderPersistenceEntity orderPersistenceEntity) {

        Set<OrderItem> newOrUpdateItems = order.items();

        if (newOrUpdateItems == null || newOrUpdateItems.isEmpty()) {
            return new HashSet<>();
        }

        Set<OrderItemPersistenceEntity> existingItems = orderPersistenceEntity.getItems();;

        if (existingItems == null || existingItems.isEmpty()) {
            return newOrUpdateItems.stream().map(i -> fromDomain(i)).collect(Collectors.toSet());
        }

        var existingItemsMap = existingItems.stream().collect(Collectors.toMap(OrderItemPersistenceEntity::getId, item -> item));

        return newOrUpdateItems.stream()
            .map(oi -> {

                var ip = existingItemsMap.getOrDefault(oi.id().value().toLong(), new OrderItemPersistenceEntity());

                return merge(ip, oi);

            }).collect(Collectors.toSet());
            
    }

    public static OrderItemPersistenceEntity fromDomain(OrderItem orderItem) {
        return merge(new OrderItemPersistenceEntity(), orderItem);
    }

    private static OrderItemPersistenceEntity merge(OrderItemPersistenceEntity orderItemPersistenceEntity,
            OrderItem orderItem) {

        orderItemPersistenceEntity.setId(orderItem.id().value().toLong());                
        orderItemPersistenceEntity.setPrice(orderItem.price().value());
        orderItemPersistenceEntity.setProductId(orderItem.productId().value());
        orderItemPersistenceEntity.setProductName(orderItem.productName().toString());
        orderItemPersistenceEntity.setQuantity(orderItem.quantity().value());
        orderItemPersistenceEntity.setTotalAmmount(orderItem.totalAmmount().value());

        return orderItemPersistenceEntity;

    }

}
