package br.com.transformers.ems.algashop.ordering.domain.model.repository;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;

public interface Customers extends Repository<Customer, CustomerId> {
    
}
