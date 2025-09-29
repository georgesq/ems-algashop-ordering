package com.algaworks.algashop.ordering.infrastructure.persistence.customer;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.algaworks.algashop.ordering.application.customer.query.CustomerOutput;
import com.algaworks.algashop.ordering.application.customer.query.CustomerQueryService;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerQueryServiceImpl implements CustomerQueryService {

    private final CustomerPersistenceEntityRepository repository;

    @Override
    public CustomerOutput findById(UUID customerId) {

        return this.repository.findByIdAsOutput(customerId).orElseThrow(() -> new CustomerNotFoundException());

    }
    
}
