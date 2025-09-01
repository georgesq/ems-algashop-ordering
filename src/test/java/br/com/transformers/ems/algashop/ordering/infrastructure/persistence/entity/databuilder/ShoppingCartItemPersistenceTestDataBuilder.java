package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder;

import java.math.BigDecimal;

import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;

public class ShoppingCartItemPersistenceTestDataBuilder {

    private ShoppingCartItemPersistenceTestDataBuilder() {

    }

    public static ShoppingCartItemPersistenceEntity aShoppingCartItem(ShoppingCartPersistenceEntity entityPersistence) {
        
        ShoppingCartItemPersistenceEntity item = ShoppingCartItemPersistenceEntity.builder()
                    .id(IdGenerator.generateUUID())
                    .price(BigDecimal.ONE)
                    .productId(IdGenerator.generateUUID())
                    .productName("pn")
                    .quantity(Long.MAX_VALUE)
                    .totalAmount(BigDecimal.TWO)
                    .shoppingCart(entityPersistence)
                .build();

        return item;

    }

}
