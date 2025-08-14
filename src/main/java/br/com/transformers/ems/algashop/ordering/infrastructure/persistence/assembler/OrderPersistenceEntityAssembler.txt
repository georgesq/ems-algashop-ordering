package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import org.springframework.stereotype.Component;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;

@Component
public class OrderPersistenceEntityAssembler {
    
    public OrderPersistenceEntity fromDomain(Order order) {
        return merge(new OrderPersistenceEntity(), order);
    }

    public OrderPersistenceEntity merge(OrderPersistenceEntity orderPersistenceEntity, Order order) {

        orderPersistenceEntity.setId(order.id().value().toLong());
        orderPersistenceEntity.setCustomerId(order.customerId().value());
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

        orderPersistenceEntity.setVersion(order.version());

        return orderPersistenceEntity;

    }
}
