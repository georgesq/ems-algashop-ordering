package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.ShoppingCart;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.ShoppingCartItem;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;

@Component
public class ShoppingCartPersistenceEntityAssembler {

    @Autowired
    private CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    public ShoppingCartPersistenceEntity fromDomain(ShoppingCart domain) {
        return merge(new ShoppingCartPersistenceEntity(), domain);
    }

    public ShoppingCartPersistenceEntity merge(ShoppingCartPersistenceEntity entity, ShoppingCart domain) {

        entity.setId(domain.id().value());
        entity.setCreatedAt(domain.createdAt());

        entity = this.setCustomer(entity, domain);

        Set<ShoppingCartItemPersistenceEntity> mergedItems = mergeItems(domain, entity);
        entity.replaceItems(mergedItems);

        return entity;
    }

    private Set<ShoppingCartItemPersistenceEntity> mergeItems(ShoppingCart domain,
            ShoppingCartPersistenceEntity entity) {

        Set<ShoppingCartItem> newOrUpdatedItems = domain.items();

        if (newOrUpdatedItems == null || newOrUpdatedItems.isEmpty()) {
            return new HashSet<>();
        }

        Set<ShoppingCartItemPersistenceEntity> existingItems = entity.getItems();

        if (existingItems == null || existingItems.isEmpty()) {
            return newOrUpdatedItems.stream()
                    .map(item -> fromDomain(item))
                    .collect(Collectors.toSet());
        }

        Map<UUID, ShoppingCartItemPersistenceEntity> existingItemMap = existingItems.stream()
                .collect(Collectors.toMap(ShoppingCartItemPersistenceEntity::getId, item -> item));

        return newOrUpdatedItems.stream()
                .map(item -> {
                    ShoppingCartItemPersistenceEntity itemPersistence = existingItemMap.getOrDefault(
                            item.id().value(), new ShoppingCartItemPersistenceEntity());
                    return merge(itemPersistence, item);
                })
                .collect(Collectors.toSet());
    }

    public ShoppingCartItemPersistenceEntity fromDomain(ShoppingCartItem domain) {

        return merge(new ShoppingCartItemPersistenceEntity(), domain);

    }

    private ShoppingCartItemPersistenceEntity merge(ShoppingCartItemPersistenceEntity entity, ShoppingCartItem domain) {

        entity.setAvailable(domain.available());
        entity.setId(domain.id().value());
        entity.setPrice(domain.price().value());
        entity.setProductId(domain.productId().value());
        entity.setProductName(domain.productName().toString());
        entity.setQuantity(domain.quantity().value());
        entity.setTotalAmount(domain.totalAmount().value());

        return entity;

    }

    private ShoppingCartPersistenceEntity setCustomer(ShoppingCartPersistenceEntity entity, ShoppingCart domain) {

        var customerPersistenceEntity = customerPersistenceEntityRepository
                .getReferenceById(domain.customerId().value());
        entity.setCustomer(customerPersistenceEntity);

        return entity;

    }
}
