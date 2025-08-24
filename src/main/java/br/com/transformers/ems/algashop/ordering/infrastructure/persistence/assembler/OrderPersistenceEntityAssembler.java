package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderItem;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderPersistenceEntityAssembler {

    private CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    public OrderPersistenceEntity fromDomain(Order order) {
        return merge(new OrderPersistenceEntity(), order);
    }

    public OrderPersistenceEntity merge(OrderPersistenceEntity orderPersistenceEntity, Order order) {

        orderPersistenceEntity.setId(order.id().value().toLong());

        var customerPersistenceEntity = customerPersistenceEntityRepository.getReferenceById(order.customer().id().value());
        orderPersistenceEntity.setCustomer(customerPersistenceEntity);

        orderPersistenceEntity.setTotalAmount(order.totalAmount().value());
        orderPersistenceEntity.setTotalItems(order.totalItems().value());
        orderPersistenceEntity.setCreatedAt(order.createdAt());
        orderPersistenceEntity.setPlacedAt(order.placedAt());
        orderPersistenceEntity.setPaidAt(order.paidAt());
        orderPersistenceEntity.setCanceledAt(order.canceledAt());
        orderPersistenceEntity.setReadAt(order.readAt());
        orderPersistenceEntity.setStatus(order.status().toString());
        orderPersistenceEntity.setPaymentMethod(order.paymentMethod().toString());

        orderPersistenceEntity.setBilling(BillingEmbeddableAssembler.fromDomain(order.billing()));
        orderPersistenceEntity.setShipping(ShippingEmbeddableAssembler.fromDomain(order.shipping()));

        Set<OrderItemPersistenceEntity> mergedItems = mergeItems(order, orderPersistenceEntity);
        orderPersistenceEntity.replaceItems(mergedItems);
        
        orderPersistenceEntity.setVersion(order.version());

        return orderPersistenceEntity;

    }

  private Set<OrderItemPersistenceEntity> mergeItems(Order order, OrderPersistenceEntity orderPersistenceEntity) {
        Set<OrderItem> newOrUpdatedItems = order.items();

        if (newOrUpdatedItems == null || newOrUpdatedItems.isEmpty()) {
            return new HashSet<>();
        }

        Set<OrderItemPersistenceEntity> existingItems = orderPersistenceEntity.getItems();
        if (existingItems == null || existingItems.isEmpty()) {
            return newOrUpdatedItems.stream()
                    .map(orderItem -> fromDomain(orderItem))
                    .collect(Collectors.toSet());
        }

        Map<Long, OrderItemPersistenceEntity> existingItemMap = existingItems.stream()
                .collect(Collectors.toMap(OrderItemPersistenceEntity::getId, item -> item));

        return newOrUpdatedItems.stream()
                .map(orderItem -> {
                    OrderItemPersistenceEntity itemPersistence = existingItemMap.getOrDefault(
                            orderItem.id().value().toLong(), new OrderItemPersistenceEntity()
                    );
                    return merge(itemPersistence, orderItem);
                })
                .collect(Collectors.toSet());
    }    

    public OrderItemPersistenceEntity fromDomain(OrderItem orderItem) {
        return merge(new OrderItemPersistenceEntity(), orderItem);
    }

    private OrderItemPersistenceEntity merge(OrderItemPersistenceEntity orderItemPersistenceEntity,
            OrderItem orderItem) {

        orderItemPersistenceEntity.setId(orderItem.id().value().toLong());
        orderItemPersistenceEntity.setPrice(orderItem.price().value());
        orderItemPersistenceEntity.setProductId(orderItem.productId().value());
        orderItemPersistenceEntity.setProductName(orderItem.productName().toString());
        orderItemPersistenceEntity.setQuantity(orderItem.quantity().value());

        return orderItemPersistenceEntity;

    }
}
