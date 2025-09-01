package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.ShoppingCart;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.ShoppingCartItem;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ProductName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ProductId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;

@Component
public class ShoppingCartPersistenceEntityDisassembler {

    public ShoppingCart toDomainEntity(ShoppingCartPersistenceEntity entity) {

        return ShoppingCart.existing()
            .createdAt(entity.getCreatedAt())
            .customerId(new CustomerId(entity.getCustomerId()))
            .id(new ShoppingCartId(entity.getId()))
            .items(this.toDomainEntity(entity.getItems()))
            
            .build();
    }

    private Set<ShoppingCartItem> toDomainEntity(Set<ShoppingCartItemPersistenceEntity> items) {

        if (items == null || items.isEmpty()) {
            return Collections.emptySet();
        }

        return items.stream().map(i -> fromEntityToModel(i)).collect(Collectors.toSet());

    }

    private ShoppingCartItem fromEntityToModel(ShoppingCartItemPersistenceEntity entity) {

        return ShoppingCartItem.existing()
            .id(new ShoppingCartItemId(entity.getId()))
            .available(entity.getAvailable())
            .price(new Money(entity.getPrice()))
            .productId(new ProductId(entity.getProductId()))
            .productName(new ProductName(entity.getProductName()))
            .quantity(new Quantity(entity.getQuantity()))
            .shoppingCartId(new ShoppingCartId(entity.getShoppingCartId()))
            .totalAmount(new Money(entity.getTotalAmount()))

            .build();

    }

}
