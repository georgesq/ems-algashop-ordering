package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import org.springframework.stereotype.Component;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Billing;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Phone;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.BillingEmbeddable;

@Component
public class BillingPersistenceEntityDisassembler {
    
    public static Billing toDomainEntity(BillingEmbeddable embeddable) {

        return Billing.builder()
            .fullName(FullNameEmbeddableDisassembler.toDomainEntity(embeddable.getFullName()))
            .email(new Email(embeddable.getEmail()))
            .phone(new Phone(embeddable.getPhone()))
            .document(new Document(embeddable.getDocument()))
            .address(AddressPersistenceEntityDisassembler.toDomainEntity(embeddable.getAddress()))
        .build();
            
    }

}
