package br.com.transformers.ems.algashop.ordering.domain.model.repository;

import java.util.Optional;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;

public interface Customers extends Repository<Customer, CustomerId> {

    Optional<Customer> ofEmail(Email email);
    
}
