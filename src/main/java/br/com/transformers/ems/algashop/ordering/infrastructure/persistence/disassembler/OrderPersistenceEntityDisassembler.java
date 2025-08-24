package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import org.springframework.stereotype.Component;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.PaymentMethod;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderPersistenceEntityDisassembler {
    
    private CustomerPersistenceEntityDisassembler customerPersistenceEntityDisassembler = new CustomerPersistenceEntityDisassembler();

    public Order toDomainEntity(OrderPersistenceEntity persistenceEntity) {

        return Order.existing()
            .id(new OrderId(persistenceEntity.getId()))
            .customer(customerPersistenceEntityDisassembler.toDomainEntity(persistenceEntity.getCustomer()))
            .totalAmount(new Money(persistenceEntity.getTotalAmount()))
            .totalItems(new Quantity(persistenceEntity.getTotalItems()))
            .createdAt(persistenceEntity.getCreatedAt())
            .placedAt(persistenceEntity.getPlacedAt())
            .paidAt(persistenceEntity.getPaidAt())
            .canceledAt(persistenceEntity.getCanceledAt())
            .readAt(persistenceEntity.getReadAt())
            .status(OrderStatus.valueOf(persistenceEntity.getStatus()))
            .paymentMethod(PaymentMethod.valueOf(persistenceEntity.getPaymentMethod()))
            .items(OrderItemPersistenceEntityDisassembler.toDomain(persistenceEntity.getItems()))

            .billing(BillingPersistenceEntityDisassembler.toDomainEntity(persistenceEntity.getBilling()))
            .shipping(ShippingPersistenceEntityDisassembler.toDomainEntity(persistenceEntity.getShipping()))

            .version(persistenceEntity.getVersion())
        .build();

    }
}
