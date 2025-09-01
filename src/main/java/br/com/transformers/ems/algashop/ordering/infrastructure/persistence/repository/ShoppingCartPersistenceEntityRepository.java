package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;

public interface ShoppingCartPersistenceEntityRepository extends JpaRepository<ShoppingCartPersistenceEntity, UUID> {

    @Query(value = """
            SELECT o
            FROM ShoppingCartPersistenceEntity o
            WHERE o.customer.id = :customerId
            """)
    Optional<ShoppingCartPersistenceEntity> findByCustomerId(@Param("customerId") String customerId);
    
}
