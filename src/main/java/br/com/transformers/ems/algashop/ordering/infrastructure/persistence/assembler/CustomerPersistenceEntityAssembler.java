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
        entity.setBirthDate(domain.birthDate().value());
        entity.setDocument(domain.document().toString());
        entity.setEmail(domain.email().toString());
        entity.setFullName(domain.fullName().toString());
        entity.setLoyaltyPoints(domain.loyaltyPoints());
        entity.setPhone(domain.phone().toString());
        entity.setPromotionNotificaficationsAllowed(domain.isPromotionNotificaficationsAllowed());
        entity.setRegisteredAt(domain.registeredAt());

        entity.setVersion(domain.version());

        return entity;

    }
    
}
