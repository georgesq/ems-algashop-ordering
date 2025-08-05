package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.provider;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.infrastructure.config.SpringDataAuditingConfig;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;

@DataJpaTest
@Import({
        OrdersPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class,
        SpringDataAuditingConfig.class
})
public class OrdersPersistenceProviderIT {

    @Autowired
    private OrdersPersistenceProvider persistenceProvider;
    @Autowired
    private OrderPersistenceEntityRepository repository;

    @Test
    @DisplayName("should update an order and maintain auditing fields")
    void shouldUpdateOrder() {
        var order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED)
                .build();

        persistenceProvider.add(order);

        var persistenceEntity = this.repository.findById(order.id().value().toLong()).orElseThrow();

        Assertions.assertThat(persistenceEntity.getCreatedByUser()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedByUser()).isNotNull();

        order.markAsPaid();
        persistenceProvider.add(order);

        persistenceEntity = this.repository.findById(order.id().value().toLong()).orElseThrow();

        Assertions.assertThat(persistenceEntity.getCreatedByUser()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedByUser()).isNotNull();        

    }

}
