package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;

public class CustomerPersistenceEntityTestDataBuilder {

    public static UUID DEFAULT_CUSTOMER_ID = new CustomerId(IdGenerator.generateUUID()).value();

    public static CustomerPersistenceEntity.CustomerPersistenceEntityBuilder aCustomer() {
        return CustomerPersistenceEntity.builder()
            .address(AddressEmbeddableTestDataBuilder.anAddress())
            .archived(false)
            .archivedAt(null)
            .birthDate(LocalDate.of(1972, 5, 29))
            .createdByUserId(UUID.randomUUID())
            .document("document")
            .email("a@ac.com")
            .firstName("george")
            .lastName("queiroz")
            .id(DEFAULT_CUSTOMER_ID)
            .lastModifiedAt(OffsetDateTime.now())
            .lastModifiedByUserId(UUID.randomUUID())
            .phone("11993044469")
            .promotionNotificationsAllowed(false)
            .registeredAt(OffsetDateTime.now())
            .loyaltyPoints(1)
            .version(1l);

    }

}
