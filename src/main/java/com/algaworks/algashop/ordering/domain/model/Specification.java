package com.algaworks.algashop.ordering.domain.model;

public interface Specification<T> {
    
    boolean isSatisfied(T t);

}
