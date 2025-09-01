package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Recipient;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.RecipientEmbeddable;

public class RecipientEmbeddableAssembler {

    public static RecipientEmbeddable fromDomain(Recipient recipient) {

        return merge(RecipientEmbeddable.builder().build(), recipient);
        
    }

    private static RecipientEmbeddable merge(RecipientEmbeddable recipientEmbeddable, Recipient recipient) {

        recipientEmbeddable.setFirstName(recipient.fullName().firstName());
        recipientEmbeddable.setLastName(recipient.fullName().lastName());
        recipientEmbeddable.setDocument(recipient.document().toString());
        recipientEmbeddable.setPhone(recipient.phone().toString());
        
        return recipientEmbeddable;
    }

}
