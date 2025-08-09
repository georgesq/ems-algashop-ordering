package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Recipient;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.RecipientEmbeddable;

public class RecipientEmbeddableAssembler {

    public static RecipientEmbeddable fromDomain(Recipient recipient) {

        return merge(RecipientEmbeddable.builder().build(), recipient);
        
    }

    private static RecipientEmbeddable merge(RecipientEmbeddable recipientEmbeddable, Recipient recipient) {

        recipientEmbeddable.setFullName(recipient.fullName().toString());
        recipientEmbeddable.setDocument(recipient.document().toString());
        recipientEmbeddable.setPhone(recipient.phone().toString());
        recipientEmbeddable.setAddress(AddressEmbeddableAssembler.fromDomain(recipient.address()));
        
        return recipientEmbeddable;
    }

}
