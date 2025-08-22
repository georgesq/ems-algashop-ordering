package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;

public class CustomerPersistenceEntityTestDataBuilder {

    public static CustomerPersistenceEntity aCustomerPersistenceEntity() {
        var customerPE = CustomerPersistenceEntity.builder()
            .address(AddressEmbeddableTestDataBuilder.anAddress())
            .archived(false)
            .archivedAt(null)
            .birthDate(LocalDate.of(1972, 5, 29))
            .createdByUserId(UUID.randomUUID())
            .document("document")
            .email("a@ac.com")
            .fullName("george queiroz")
            .id(UUID.randomUUID())
            .lastModifiedAt(OffsetDateTime.now())
            .lastModifiedByUserId(UUID.randomUUID())
            .phone("11993044469")
            .promotionNotificaficationsAllowed(false)
            .registeredAt(OffsetDateTime.now())
            .loyaltyPoints(1)
            .version(1l)
        .build();

        return customerPE;
    }

}
