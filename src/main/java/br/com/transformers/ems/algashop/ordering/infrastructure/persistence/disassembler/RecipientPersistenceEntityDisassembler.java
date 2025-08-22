package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Phone;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Recipient;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.RecipientEmbeddable;

public class RecipientPersistenceEntityDisassembler {

    public static Recipient toDomainEntity(RecipientEmbeddable recipient) {
        return Recipient.builder()
            .fullName(FullNameEmbeddablePersistenceEntityDisassembler.toDomainEntity(recipient.getFullName()))
            .phone(new Phone(recipient.getPhone()))
            .document(new Document(recipient.getDocument()))
            .address(AddressEmbeddablePersistenceEntityDisassembler.toDomainEntity(recipient.getAddress()))
        .build();
    }

}
