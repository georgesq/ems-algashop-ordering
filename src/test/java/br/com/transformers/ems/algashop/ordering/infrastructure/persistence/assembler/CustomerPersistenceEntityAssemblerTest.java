package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;

class CustomerPersistenceEntityAssemblerTest {

    private CustomerPersistenceEntityAssembler assembler;

    @BeforeEach
    void setUp() {
         assembler = new CustomerPersistenceEntityAssembler();
    }

    @Test
    void fromDomain_shouldMapAllFields() {
        Customer domain = CustomerTestDataBuilder.aCustomerBuilder().build();

        // Act
        CustomerPersistenceEntity entity = assembler.fromDomain(domain);

        // Assert
        assertThat(entity.getId().toString()).isEqualTo(domain.id().toString());
        assertThat(entity.getAddress()).isNotNull();
        assertThat(entity.getArchived()).isFalse();
        assertThat(entity.getBirthDate()).isEqualTo(domain.birthDate().toString());
        assertThat(entity.getDocument()).isEqualTo(domain.document().toString());
        assertThat(entity.getEmail()).isEqualTo(domain.email().toString());
        assertThat(entity.getFullName()).isEqualTo(domain.fullName().toString());
        assertThat(entity.getLoyaltyPoints()).isNotNull();
        assertThat(entity.getPhone()).isEqualTo(domain.phone().toString());
        assertThat(entity.getPromotionNotificaficationsAllowed()).isFalse();
        assertThat(entity.getVersion()).isNull();

    }

}