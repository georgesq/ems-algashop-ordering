package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.provider;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.model.repository.Customers;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomersPersistenceProvider implements Customers {

    private final CustomerPersistenceEntityRepository repository;
    private final CustomerPersistenceEntityAssembler assembler;
    private final CustomerPersistenceEntityDisassembler disassembler;
    private final EntityManager entityManager;

    @Override
    public Optional<Customer> ofId(CustomerId id) {

        var optionalPersistenceEntity = repository.findById(id.value());

        return optionalPersistenceEntity.map(disassembler::toDomainEntity);

    }

    @Override
    public Boolean exists(CustomerId id) {
        return this.repository.existsById(id.value());
    }

    @Override
    @Transactional(readOnly = false)
    public void add(Customer aggregateRoot) {

        var id = aggregateRoot.id().value();

        this.repository.findById(id).ifPresentOrElse(
            (pe) -> {
                update(aggregateRoot, pe);
            }, 
            () -> {
                insert(aggregateRoot);
            }
        );

    }

    private void update(Customer aggregateRoot, CustomerPersistenceEntity persistenceEntity) {

        CustomerPersistenceEntity merged = assembler.merge(persistenceEntity, aggregateRoot);

        this.entityManager.detach(persistenceEntity);

        persistenceEntity = this.repository.saveAndFlush(merged);

        updateVersion(aggregateRoot, persistenceEntity);

    }

    private void insert(Customer aggregateRoot) {

        var persistenceEntity = assembler.fromDomain(aggregateRoot);

        this.repository.saveAndFlush(persistenceEntity);

        updateVersion(aggregateRoot, persistenceEntity);

    }

    private void updateVersion(Customer aggregateRoot, CustomerPersistenceEntity persistenceEntity) {

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

    @Override
    public Optional<Customer> ofEmail(Email email) {
        return this.repository.findByEmail(email.toString())
            .map(disassembler::toDomainEntity);
    }

    @Override
    public Boolean isEmailUnique(Email email, CustomerId customerId) {

        return !this.repository.existsByEmailAndIdNot(email.value(), customerId.value());

    }

}
