package com.algaworks.algashop.ordering.infrastructure.persistence.customer;

import java.util.Optional;
import java.util.UUID;

import com.algaworks.algashop.ordering.application.customer.query.CustomerOutput;

public interface CustomerPersistenceEntityQueries {
    
    Optional<CustomerOutput> findByIdAsOutput(UUID customerId);

}
