package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import org.springframework.stereotype.Component;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Customer;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;

@Component
public class CustomerPersistenceEntityAssembler {
    
    public CustomerPersistenceEntity fromDomain(Customer domain) {
        return merge(new CustomerPersistenceEntity(), domain);
    }

    public CustomerPersistenceEntity merge(CustomerPersistenceEntity entity, Customer domain) {

        entity.setId(domain.id().value());
        entity.setAddress(AddressEmbeddableAssembler.fromDomain(domain.address()));
        entity.setArchived(domain.isArchived());
        entity.setArchivedAt(domain.archivedAt());
        entity.setBirthDate(domain.birthDate().value());
        entity.setDocument(domain.document().toString());
        entity.setEmail(domain.email().toString());
        entity.setFirstName(domain.fullName().firstName());
        entity.setLastName(domain.fullName().lastName());
        entity.setLoyaltyPoints(domain.loyaltyPoints().value());
        entity.setPhone(domain.phone().toString());
        entity.setPromotionNotificationsAllowed(domain.isPromotionNotificationsAllowed());
        entity.setRegisteredAt(domain.registeredAt());

        entity.setVersion(domain.version());

        return entity;

    }
    
}
