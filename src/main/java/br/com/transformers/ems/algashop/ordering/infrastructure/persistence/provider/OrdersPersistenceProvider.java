package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.provider;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.repository.Orders;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.OrderPersistenceEntityAssembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdersPersistenceProvider implements Orders {

    private final OrderPersistenceEntityRepository repository;
    private final OrderPersistenceEntityAssembler assembler;
    private final OrderPersistenceEntityDisassembler disassembler;
    private final EntityManager entityManager;

    @Override
    public Optional<Order> ofId(OrderId id) {

        var optionalPersistenceEntity = repository.findById(id.value().toLong());

        return optionalPersistenceEntity.map(disassembler::toDomainEntity);

    }

    @Override
    public Boolean exists(OrderId id) {
        return this.repository.existsById(id.value().toLong());
    }

    @Override
    @Transactional(readOnly = false)
    public void add(Order aggregateRoot) {

        long orderId = aggregateRoot.id().value().toLong();

        this.repository.findById(orderId).ifPresentOrElse(
            (pe) -> {
                update(aggregateRoot, pe);
            }, 
            () -> {
                insert(aggregateRoot);
            }
        );

    }

    private void update(Order aggregateRoot, OrderPersistenceEntity persistenceEntity) {

        OrderPersistenceEntity merged = assembler.merge(persistenceEntity, aggregateRoot);

        this.entityManager.detach(persistenceEntity);

        persistenceEntity = this.repository.saveAndFlush(merged);

        updateVersion(aggregateRoot, persistenceEntity);

    }

    private void insert(Order aggregateRoot) {

        OrderPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);

        this.repository.saveAndFlush(persistenceEntity);

        updateVersion(aggregateRoot, persistenceEntity);

    }

    private void updateVersion(Order aggregateRoot, OrderPersistenceEntity persistenceEntity) {

        Field field = ReflectionUtils.findField(aggregateRoot.getClass(), "version");;
        Objects.requireNonNull(field);
        field.setAccessible(true);

        ReflectionUtils.setField(field, aggregateRoot, persistenceEntity.getVersion());

        field.setAccessible(false);
        
    }


    @Override
    public Long count() {
        return this.repository.count();
    }

}
