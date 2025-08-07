package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.provider;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.repository.Orders;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
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

        aggregateRoot.setVersion(persistenceEntity.getVersion());

    }

    private void insert(Order aggregateRoot) {

        OrderPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);

        this.repository.saveAndFlush(persistenceEntity);

        aggregateRoot.setVersion(persistenceEntity.getVersion());

    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

}
