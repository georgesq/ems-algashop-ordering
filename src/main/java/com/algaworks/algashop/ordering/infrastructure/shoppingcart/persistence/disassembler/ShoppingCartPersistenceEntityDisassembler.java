package com.algaworks.algashop.ordering.infrastructure.shoppingcart.persistence.disassembler;

import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.customer.valueobject.CustomerId;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductId;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.entity.ShoppingCartItem;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.valueobject.ShoppingCartId;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.valueobject.ShoppingCartItemId;
import com.algaworks.algashop.ordering.infrastructure.shoppingcart.persistence.entity.ShoppingCartItemPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.shoppingcart.persistence.entity.ShoppingCartPersistenceEntity;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ShoppingCartPersistenceEntityDisassembler {
    public ShoppingCart toDomainEntity(ShoppingCartPersistenceEntity source) {
        return ShoppingCart.existing()
                .id(new ShoppingCartId(source.getId()))
                .customerId(new CustomerId(source.getCustomerId()))
                .totalAmount(new Money(source.getTotalAmount()))
                .createdAt(source.getCreatedAt())
                .items(toItemsDomainEntities(source.getItems()))
                .totalItems(new Quantity(source.getTotalItems()))
                .build();
    }

    private Set<ShoppingCartItem> toItemsDomainEntities(Set<ShoppingCartItemPersistenceEntity> source) {
        return source.stream().map(this::toItemEntity).collect(Collectors.toSet());
    }

    private ShoppingCartItem toItemEntity(ShoppingCartItemPersistenceEntity source) {
        return ShoppingCartItem.existing()
                .id(new ShoppingCartItemId(source.getId()))
                .shoppingCartId(new ShoppingCartId(source.getShoppingCartId()))
                .productId(new ProductId(source.getProductId()))
                .productName(new ProductName(source.getName()))
                .price(new Money(source.getPrice()))
                .quantity(new Quantity(source.getQuantity()))
                .available(source.getAvailable())
                .totalAmount(new Money(source.getTotalAmount()))
                .build();
    }
}
