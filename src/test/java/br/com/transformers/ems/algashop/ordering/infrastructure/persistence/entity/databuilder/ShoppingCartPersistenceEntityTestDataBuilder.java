package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;

import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity.ShoppingCartPersistenceEntityBuilder;

public class ShoppingCartPersistenceEntityTestDataBuilder {
    public static ShoppingCartPersistenceEntityBuilder existingShoppingCart() {
        var items = new HashSet<ShoppingCartItemPersistenceEntity>(2);
        items.add(existingItem().build());
        items.add(existingItemAlt().build());

        var response = ShoppingCartPersistenceEntity.builder()
                .id(IdGenerator.generateUUID())
                .customer(CustomerPersistenceEntityTestDataBuilder.aCustomer().build())
                .totalItems(3l)
                .totalAmount(new BigDecimal(1250))
                .createdAt(OffsetDateTime.now())
                .items(items);

        return response;

    }

    public static ShoppingCartItemPersistenceEntity.ShoppingCartItemPersistenceEntityBuilder existingItem() {
        return ShoppingCartItemPersistenceEntity.builder()
                .id(IdGenerator.generateUUID())
                .price(new BigDecimal(500))
                .quantity(2l)
                .totalAmount(new BigDecimal(1000))
                .productName("Notebook")
                .productId(IdGenerator.generateUUID());
    }

    public static ShoppingCartItemPersistenceEntity.ShoppingCartItemPersistenceEntityBuilder existingItemAlt() {
        return ShoppingCartItemPersistenceEntity.builder()
                .id(IdGenerator.generateUUID())
                .price(new BigDecimal(250))
                .quantity(1l)
                .totalAmount(new BigDecimal(250))
                .productName("Mouse pad")
                .productId(IdGenerator.generateUUID());
    }

}