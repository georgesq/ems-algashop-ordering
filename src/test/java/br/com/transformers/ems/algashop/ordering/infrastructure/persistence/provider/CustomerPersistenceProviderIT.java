package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.provider;

import java.time.OffsetDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.LoyaltPoints;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.infrastructure.config.JpaConfig;
import br.com.transformers.ems.algashop.ordering.infrastructure.config.SpringDataAuditingConfig;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;

@DataJpaTest
@Import({
        CustomerPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class,
        SpringDataAuditingConfig.class,
        JpaConfig.class
})
class CustomerPersistenceProviderIT {

    @Autowired
    private CustomerPersistenceProvider provider;

    @BeforeEach
    void setUp() {
    }

    @Test
    void givenCustomerWhenAddThenSaved() {
        Customer customer = CustomerTestDataBuilder.aCustomerBuilder()
            .build();

        this.provider.add(customer);

        Customer saved = this.provider.ofId(customer.id()).orElseThrow();

        Assertions.assertThat(saved).satisfies(c -> {
            Assertions.assertThat(c.isArchived()).isEqualTo(customer.isArchived());
            Assertions.assertThat(c.address()).isEqualTo(customer.address());
            Assertions.assertThat(c.isPromotionNotificaficationsAllowed()).isEqualTo(customer.isPromotionNotificaficationsAllowed());
            Assertions.assertThat(c.loyaltyPoints()).isEqualTo(customer.loyaltyPoints());
            Assertions.assertThat(c.registeredAt()).isEqualTo(customer.registeredAt());
            Assertions.assertThat(c.version()).isEqualTo(customer.version());
            Assertions.assertThat(c.birthDate()).isEqualTo(customer.birthDate());
            Assertions.assertThat(c.document()).isEqualTo(customer.document());
            Assertions.assertThat(c.email()).isEqualTo(customer.email());
        });
    }

    @Test
    void givenSavedCustomerWhenChangedThenUpdated() {
        Customer customer = CustomerTestDataBuilder.aCustomerBuilder()
            .build();

        this.provider.add(customer);

        Customer saved = this.provider.ofId(customer.id()).orElseThrow();

        saved.changeEmail(new Email("b@b.com"));

        this.provider.add(saved);

        var changed = this.provider.ofId(customer.id()).orElseThrow();

        Assertions.assertThat(saved).satisfies(c -> {
            Assertions.assertThat(c.version()).isEqualTo(changed.version());
            Assertions.assertThat(c.email()).isEqualTo(changed.email());
        });

    }

    @Test
    void givenOlderCustomerWhenChangedThenObjectOptimisticLockingFailureException() {
        Customer customer = CustomerTestDataBuilder.aCustomerBuilder()
            .build();

        CustomerId id = customer.id();

        this.provider.add(customer);

        Customer newCustomer = Customer.existing()
            .id(id)
            .email(new Email("b@b.com"))
            .fullName(customer.fullName())
            .address(customer.address())
            .archivedAt(customer.archivedAt())
            .birthDate(customer.birthDate())
            .document(customer.document())
            .loyaltyPoints(new LoyaltPoints(customer.loyaltyPoints()))
            .phone(customer.phone())
            .promotionNotificaficationsAllowed(customer.isPromotionNotificaficationsAllowed())
            .registeredAt(OffsetDateTime.now())
            .archived(false)
            .build();        

        Assertions.assertThatException().isThrownBy(() -> this.provider.add(newCustomer));
    }
}