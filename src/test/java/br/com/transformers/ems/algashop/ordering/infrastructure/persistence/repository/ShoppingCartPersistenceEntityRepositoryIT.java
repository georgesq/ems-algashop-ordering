package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.infrastructure.config.JpaConfig;
import br.com.transformers.ems.algashop.ordering.infrastructure.config.SpringDataAuditingConfig;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder.CustomerPersistenceEntityTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder.ShoppingCartPersistenceEntityTestDataBuilder;

@DataJpaTest
@Import({
    SpringDataAuditingConfig.class, 
    JpaConfig.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShoppingCartPersistenceEntityRepositoryIT {

    private final ShoppingCartPersistenceEntityRepository shoppingCartPersistenceEntityRepository;
    private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    private CustomerPersistenceEntity customerPersistenceEntity;

    @Autowired
    public ShoppingCartPersistenceEntityRepositoryIT(ShoppingCartPersistenceEntityRepository shoppingCartPersistenceEntityRepository,
                                                     CustomerPersistenceEntityRepository customerPersistenceEntityRepository) {
        this.shoppingCartPersistenceEntityRepository = shoppingCartPersistenceEntityRepository;
        this.customerPersistenceEntityRepository = customerPersistenceEntityRepository;
    }

    @BeforeEach
    public void setup() {
        UUID customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID.value();
        if (!customerPersistenceEntityRepository.existsById(customerId)) {
            customerPersistenceEntity = customerPersistenceEntityRepository.saveAndFlush(
                    CustomerPersistenceEntityTestDataBuilder.aCustomer().build()
            );
        }
    }

    @Test
    public void shouldPersist() {
        ShoppingCartPersistenceEntity entity = ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart()
                .customer(customerPersistenceEntity)
                .build();

        shoppingCartPersistenceEntityRepository.saveAndFlush(entity);
        Assertions.assertThat(shoppingCartPersistenceEntityRepository.existsById(entity.getId())).isTrue();

        ShoppingCartPersistenceEntity savedEntity = shoppingCartPersistenceEntityRepository.findById(entity.getId()).orElseThrow();

        Assertions.assertThat(savedEntity.getItems()).isNotEmpty();
    }

    @Test
    public void shouldCount() {
        long shoppingCartsCount = shoppingCartPersistenceEntityRepository.count();
        Assertions.assertThat(shoppingCartsCount).isZero();
    }

    @Test
    public void shouldSetAuditingValues() {
        ShoppingCartPersistenceEntity entity = ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart()
                .customer(customerPersistenceEntity)
                .build();
        entity = shoppingCartPersistenceEntityRepository.saveAndFlush(entity);

        Assertions.assertThat(entity.getCreatedByUser()).isNotNull();

        Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedByUser()).isNotNull();
    }

}