package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;

public interface OrderPersistenceEntityRepository extends JpaRepository<OrderPersistenceEntity, Long> {

    @Query(value = 
        """
            SELECT o
            FROM OrderPersistenceEntity o
            WHERE o.customer.id = :customerId AND
                YEAR(o.placedAt) = :year
        """)
    List<OrderPersistenceEntity> placedByCustomerInYear(
        @Param("customerId") UUID customerId, 
        @Param("year") Integer year
    );

}
