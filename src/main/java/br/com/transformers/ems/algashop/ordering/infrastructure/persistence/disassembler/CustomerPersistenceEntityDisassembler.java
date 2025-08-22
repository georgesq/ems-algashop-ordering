package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import org.springframework.stereotype.Component;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.BirthDate;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.LoyaltPoints;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Phone;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;

@Component
public class CustomerPersistenceEntityDisassembler {
    
    public Customer toDomainEntity(CustomerPersistenceEntity persistenceEntity) {

        return Customer.existing()
            .id(new CustomerId(persistenceEntity.getId()))
            .address(AddressEmbeddablePersistenceEntityDisassembler.toDomainEntity(persistenceEntity.getAddress()))
            .archived(persistenceEntity.getArchived())
            .archivedAt(persistenceEntity.getArchivedAt())
            .birthDate(new BirthDate(persistenceEntity.getBirthDate()))
            .document(new Document(persistenceEntity.getDocument()))
            .email(new Email(persistenceEntity.getEmail()))
            .fullName(FullNameEmbeddablePersistenceEntityDisassembler.toDomainEntity(persistenceEntity.getFullName()))
            .loyaltyPoints(new LoyaltPoints(persistenceEntity.getLoyaltyPoints()))
            .phone(new Phone(persistenceEntity.getPhone()))
            .promotionNotificaficationsAllowed(persistenceEntity.getPromotionNotificaficationsAllowed())
            .registeredAt(persistenceEntity.getRegisteredAt())

            .version(persistenceEntity.getVersion())

            .build();

    }
}
