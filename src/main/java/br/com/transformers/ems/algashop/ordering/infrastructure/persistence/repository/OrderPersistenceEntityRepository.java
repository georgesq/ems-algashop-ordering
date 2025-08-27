package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;

public interface OrderPersistenceEntityRepository extends JpaRepository<OrderPersistenceEntity, Long> {

    List<OrderPersistenceEntity> findByCustomer_IdAndPlacedAtBetween(UUID customerId, OffsetDateTime start, OffsetDateTime end);

}
