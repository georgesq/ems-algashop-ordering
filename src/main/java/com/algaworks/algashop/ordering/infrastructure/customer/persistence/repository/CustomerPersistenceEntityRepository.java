package com.algaworks.algashop.ordering.infrastructure.customer.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algashop.ordering.infrastructure.customer.persistence.entity.CustomerPersistenceEntity;

import java.util.Optional;
import java.util.UUID;

public interface CustomerPersistenceEntityRepository extends JpaRepository<CustomerPersistenceEntity, UUID> {
    Optional<CustomerPersistenceEntity> findByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID customerId);
}