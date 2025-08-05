package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.PaymentMethod;
import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;

@DataJpaTest
@Transactional
class OrderPersistenceEntityRepositoryIT {

    @Autowired
    private OrderPersistenceEntityRepository repository;

    private OrderPersistenceEntity order;

    @BeforeEach
    void setUp() {

        order = OrderPersistenceEntity.builder()
            .id(IdGenerator.generateTSID().toLong())
            .customerId(IdGenerator.generateUUID())
            .totalAmount(BigDecimal.valueOf(100))
            .createdAt(OffsetDateTime.now())
            .status(OrderStatus.DRAFT.toString())
            .paymentMethod(PaymentMethod.CREDIT_CARD.toString())
        .build();


    }

    @Test
    @DisplayName("should save and retrieve an order")
    void saveAndFindById() {

        OrderPersistenceEntity saved = repository.save(order);

        Assertions.assertThat(repository.existsById(saved.getId())).isTrue();

    }

    @Test
    @DisplayName("should return empty when order not found")
    void findByIdNotFound() {
        Optional<OrderPersistenceEntity> found = repository.findById(999L);

        assertThat(found).isNotPresent();
    }

    @Test
    @DisplayName("should delete an order")
    void deleteOrder() {
        OrderPersistenceEntity saved = repository.save(order);

        repository.deleteById(saved.getId());

        Optional<OrderPersistenceEntity> found = repository.findById(saved.getId());
        assertThat(found).isNotPresent();
    }

    @Test
    @DisplayName("should find all orders")
    void findAllOrders() {

        repository.save(order);

        OrderPersistenceEntity anotherOrder = new OrderPersistenceEntity();

        anotherOrder.setId(IdGenerator.generateTSID().toLong());
        anotherOrder.setCustomerId(IdGenerator.generateUUID());
        anotherOrder.setTotalAmount(BigDecimal.valueOf(200.00));
        anotherOrder.setCreatedAt(OffsetDateTime.now());
        repository.save(anotherOrder);

        assertThat(repository.findAll()).hasSizeGreaterThanOrEqualTo(2);
    }
}