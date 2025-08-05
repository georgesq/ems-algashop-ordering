package br.com.transformers.ems.algashop.ordering.domain.model.repository;

import java.util.Optional;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.AggregateRoot;

public interface Repository <T extends AggregateRoot<ID>, ID>{
    
    Optional<T> ofId(ID id);

    Boolean exists(ID id);

    void add(T aggregateRoot);

    int count();

}
