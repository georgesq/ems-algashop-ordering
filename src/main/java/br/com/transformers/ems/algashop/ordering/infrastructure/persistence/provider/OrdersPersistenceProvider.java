package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.provider;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.repository.Orders;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrdersPersistenceProvider implements Orders {

    private final OrderPersistenceEntityRepository repository;
    private final OrderPersistenceEntityAssembler assembler;
    private final OrderPersistenceEntityDisassembler disassembler;

    @Override
    public Optional<Order> ofId(OrderId id) {

        var optionalPersistenceEntity = repository.findById(id.value().toLong());

        return optionalPersistenceEntity.map(disassembler::toDomainEntity);

    }

    @Override
    public Boolean exists(OrderId id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exists'");
    }

    @Override
    public void add(Order aggregateRoot) {

        var orderPersistenceEntity = this.assembler.fromDomain(aggregateRoot);

        this.repository.saveAndFlush(orderPersistenceEntity);

    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

}
