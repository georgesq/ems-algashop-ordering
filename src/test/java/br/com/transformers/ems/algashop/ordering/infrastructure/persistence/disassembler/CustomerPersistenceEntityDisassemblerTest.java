package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.BirthDate;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.FullName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder.CustomerPersistenceEntityTestDataBuilder;

class CustomerPersistenceEntityDisassemblerTest {

    private CustomerPersistenceEntityDisassembler disassembler;

    @BeforeEach
    void setUp() {
         disassembler = new CustomerPersistenceEntityDisassembler();
    }

    @Test
    void toDomainEntity_shouldMapAllFieldsCorrectly() {
        // Arrange
        CustomerPersistenceEntity persistenceEntity = CustomerPersistenceEntityTestDataBuilder.aCustomer().build();

        var disassembled = disassembler.toDomainEntity(persistenceEntity);

        Assertions.assertThat(disassembled).satisfies(d -> {
            assertThat(d.id()).isEqualTo(new CustomerId(persistenceEntity.getId()));
            assertThat(d.address()).isEqualTo(AddressEmbeddablePersistenceEntityDisassembler.toDomainEntity(persistenceEntity.getAddress()));
            assertThat(d.isArchived()).isEqualTo(persistenceEntity.getArchived());
            assertThat(d.archivedAt()).isEqualTo(persistenceEntity.getArchivedAt());
            assertThat(d.birthDate()).isEqualTo(new BirthDate(persistenceEntity.getBirthDate()));
            assertThat(d.document()).isEqualTo(new Document(persistenceEntity.getDocument()));
            assertThat(d.email()).isEqualTo(new Email(persistenceEntity.getEmail()));
            assertThat(d.fullName()).isEqualTo(new FullName(persistenceEntity.getFirstName(), persistenceEntity.getLastName()));
            assertThat(d.isPromotionNotificationsAllowed()).isEqualTo(persistenceEntity.getPromotionNotificationsAllowed());
        });

    }

}