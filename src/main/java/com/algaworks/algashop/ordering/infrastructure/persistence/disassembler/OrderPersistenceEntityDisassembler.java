package com.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderItem;
import com.algaworks.algashop.ordering.domain.model.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.entity.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.model.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderItemId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;

@Component
public class OrderPersistenceEntityDisassembler {

    public Order toDomainEntity(OrderPersistenceEntity persistenceEntity) {
        return Order.existing()
                .id(new OrderId(persistenceEntity.getId()))
                .customerId(new CustomerId(persistenceEntity.getCustomerId()))
                .totalAmount(new Money(persistenceEntity.getTotalAmount()))
                .totalItems(new Quantity(persistenceEntity.getTotalItems()))
                .status(OrderStatus.valueOf(persistenceEntity.getStatus()))
                .paymentMethod(PaymentMethod.valueOf(persistenceEntity.getPaymentMethod()))
                .placedAt(persistenceEntity.getPlacedAt())
                .paidAt(persistenceEntity.getPaidAt())
                .canceledAt(persistenceEntity.getCanceledAt())
                .readyAt(persistenceEntity.getReadyAt())
                .items(new HashSet<>())
                .version(persistenceEntity.getVersion())
                .items(toDomainEntity(persistenceEntity.getItems()))
                .build();
    }

    private Set<OrderItem> toDomainEntity(Set<OrderItemPersistenceEntity> items) {
        return items.stream().map(i -> toDomainEntity(i)).collect(Collectors.toSet());
    }

    private OrderItem toDomainEntity(OrderItemPersistenceEntity persistenceEntity) {
        return OrderItem.existing()
                .id(new OrderItemId(persistenceEntity.getId()))
                .orderId(new OrderId(persistenceEntity.getOrderId()))
                .productId(new ProductId(persistenceEntity.getProductId()))
                .productName(new ProductName(persistenceEntity.getProductName()))
                .price(new Money(persistenceEntity.getPrice()))
                .quantity(new Quantity(persistenceEntity.getQuantity()))
                .totalAmount(new Money(persistenceEntity.getTotalAmount()))
                .build();
    }

}