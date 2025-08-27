package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;

public interface CustomerPersistenceEntityRepository extends JpaRepository<CustomerPersistenceEntity, UUID> {

    Optional<CustomerPersistenceEntity> findByEmail(String string);

    @Query(value = """
        SELECT EXISTS(
            SELECT o.id FROM CustomerPersistenceEntity o
            WHERE o.email = :email AND
                o.id = :customerId
        )        
    """)
    Boolean isEmailUnique(String email, UUID customerId);

}
