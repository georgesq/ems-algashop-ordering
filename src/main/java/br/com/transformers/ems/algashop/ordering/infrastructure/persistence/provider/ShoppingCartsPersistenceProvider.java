package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.provider;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.ShoppingCart;
import br.com.transformers.ems.algashop.ordering.domain.model.repository.ShoppingCarts;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler.ShoppingCartPersistenceEntityAssembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler.ShoppingCartPersistenceEntityDisassembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository.ShoppingCartPersistenceEntityRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShoppingCartsPersistenceProvider implements ShoppingCarts {

    private final ShoppingCartPersistenceEntityRepository repository;
    private final ShoppingCartPersistenceEntityAssembler assembler;
    private final ShoppingCartPersistenceEntityDisassembler disassembler;

    private final EntityManager entityManager;

    @Override
    public Optional<ShoppingCart> ofId(ShoppingCartId id) {

        return this.repository.findById(id.value()).map(disassembler::toDomainEntity);

    }

    @Override
    public Boolean exists(ShoppingCartId id) {

        return this.repository.existsById(id.value());

    }

    @Override
    @Transactional(readOnly = false)
    public void add(ShoppingCart aggregateRoot) {

        var orderId = aggregateRoot.id().value();

        this.repository.findById(orderId).ifPresentOrElse(
                (pe) -> {
                    update(aggregateRoot, pe);
                },
                () -> {
                    insert(aggregateRoot);
                });

    }

    private void update(ShoppingCart aggregateRoot, ShoppingCartPersistenceEntity persistenceEntity) {

        ShoppingCartPersistenceEntity merged = assembler.merge(persistenceEntity, aggregateRoot);

        this.entityManager.detach(persistenceEntity);

        persistenceEntity = this.repository.saveAndFlush(merged);

        updateVersion(aggregateRoot, persistenceEntity);

    }

    private void insert(ShoppingCart aggregateRoot) {

        ShoppingCartPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);

        this.repository.saveAndFlush(persistenceEntity);

        updateVersion(aggregateRoot, persistenceEntity);

    }

    private void updateVersion(ShoppingCart aggregateRoot, ShoppingCartPersistenceEntity persistenceEntity) {

        Field field = ReflectionUtils.findField(aggregateRoot.getClass(), "version");
        
        Objects.requireNonNull(field);
        field.setAccessible(true);

        ReflectionUtils.setField(field, aggregateRoot, persistenceEntity.getVersion());

        field.setAccessible(false);

    }

    @Override
    public Long count() {

        return this.repository.count();

    }

    @Override
    public Optional<ShoppingCart> ofCustomer(CustomerId customerId) {

        return this.repository.findByCustomerId(customerId.toString()).map(disassembler::toDomainEntity);
        
    }

}
