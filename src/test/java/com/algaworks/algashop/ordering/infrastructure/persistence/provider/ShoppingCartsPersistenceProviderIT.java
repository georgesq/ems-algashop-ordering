package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartId;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomersPersistenceProvider;
import com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.ShoppingCartPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.ShoppingCartPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.ShoppingCartPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.ShoppingCartsPersistenceProvider;

@DataJpaTest
@Import({
        ShoppingCartsPersistenceProvider.class,
        ShoppingCartPersistenceEntityAssembler.class,
        ShoppingCartPersistenceEntityDisassembler.class,
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class,
        SpringDataAuditingConfig.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = "spring.flyway.locations=classpath:db/migration,classpath:db/testdata")
class ShoppingCartsPersistenceProviderIT {

    private ShoppingCartsPersistenceProvider persistenceProvider;
    private CustomersPersistenceProvider customersPersistenceProvider;
    private ShoppingCartPersistenceEntityRepository entityRepository;

    @Autowired
    public ShoppingCartsPersistenceProviderIT(ShoppingCartsPersistenceProvider persistenceProvider,
                                              CustomersPersistenceProvider customersPersistenceProvider,
                                              ShoppingCartPersistenceEntityRepository entityRepository) {
        this.persistenceProvider = persistenceProvider;
        this.customersPersistenceProvider = customersPersistenceProvider;
        this.entityRepository = entityRepository;
    }

    @Test
    public void shouldAddAndFindShoppingCart() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        assertThat(shoppingCart.version()).isNull();

        persistenceProvider.add(shoppingCart);

        assertThat(shoppingCart.version()).isNotNull().isEqualTo(0L);

        ShoppingCart foundCart = persistenceProvider.ofId(shoppingCart.id()).orElseThrow();
        assertThat(foundCart).isNotNull();
        assertThat(foundCart.id()).isEqualTo(shoppingCart.id());
        assertThat(foundCart.totalItems().value()).isEqualTo(3);
    }

    @Test
    public void shouldRemoveShoppingCartById() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        persistenceProvider.add(shoppingCart);
        assertThat(persistenceProvider.exists(shoppingCart.id())).isTrue();

        persistenceProvider.remove(shoppingCart.id());

        assertThat(persistenceProvider.exists(shoppingCart.id())).isFalse();
        assertThat(entityRepository.findById(shoppingCart.id().value())).isEmpty();
    }
    
    @Test
    public void shouldRemoveShoppingCartByEntity() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        persistenceProvider.add(shoppingCart);
        assertThat(persistenceProvider.exists(shoppingCart.id())).isTrue();

        persistenceProvider.remove(shoppingCart);

        assertThat(persistenceProvider.exists(shoppingCart.id())).isFalse();
    }

    @Test
    public void shouldFindShoppingCartByCustomerId() {

        ShoppingCart foundCart = persistenceProvider.ofCustomer(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID).orElseThrow();

        assertThat(foundCart).isNotNull();
        assertThat(foundCart.customerId()).isEqualTo(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID);
        assertThat(foundCart.id()).isEqualTo(new ShoppingCartId("9c8d7e6f-5a4b-3c2d-1e0f-9d8c7b6a5f4e"));
    }

    @Test
    public void shouldCorrectlyCountShoppingCarts() {
        long initialCount = persistenceProvider.count();

        ShoppingCart cart1 = ShoppingCartTestDataBuilder.aShoppingCart().build();
        persistenceProvider.add(cart1);
        
        Customer otherCustomer = CustomerTestDataBuilder.existingCustomer().id(new CustomerId()).build();
        customersPersistenceProvider.add(otherCustomer);

        ShoppingCart cart2 = ShoppingCartTestDataBuilder.aShoppingCart().customerId(otherCustomer.id()).build();
        persistenceProvider.add(cart2);

        long finalCount = persistenceProvider.count();

        assertThat(finalCount).isEqualTo(initialCount + 2);
    }
    
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void shouldAddAndFindWhenNoTransaction() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();

        persistenceProvider.add(shoppingCart);

        assertThatNoException().isThrownBy(() -> {
            ShoppingCart foundCart = persistenceProvider.ofId(shoppingCart.id()).orElseThrow();
            assertThat(foundCart).isNotNull();
        });
    }
}