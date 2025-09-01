package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.RecipientEmbeddable;

public class RecipientEmbeddableTestDataBuilder {

    public static RecipientEmbeddable aRecipient() {
        return RecipientEmbeddable.builder()
                .document("123-456-7890")
                .firstName("Jane")
                .lastName("Doe")
                .phone("123-456-7890")
            .build();
    }

}
