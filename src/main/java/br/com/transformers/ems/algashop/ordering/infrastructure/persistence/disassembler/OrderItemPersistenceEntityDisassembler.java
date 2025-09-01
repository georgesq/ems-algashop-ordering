package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderItem;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ProductName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderItemId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ProductId;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;

public class OrderItemPersistenceEntityDisassembler {

    public static Set<OrderItem> toDomain(Set<OrderItemPersistenceEntity> items) {
        if (items == null || items.isEmpty()) {
            return Collections.emptySet();
        }

        return items.stream().map(i -> fromEntityToModel(i)).collect(Collectors.toSet());
    }

    private static OrderItem fromEntityToModel(OrderItemPersistenceEntity oip) {
        return OrderItem.existing()
            .id(new OrderItemId(oip.getId()))
            .orderId(new OrderId(oip.getId()))
            .price(new Money(oip.getPrice()))
            .productId(new ProductId(oip.getProductId()))
            .productName(new ProductName(oip.getProductName()))
            .quantity(new Quantity(oip.getQuantity()))
            .totalAmount(new Money(oip.getTotalAmount()))
            .build();
    }

}
