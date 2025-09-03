package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.Document;
import com.algaworks.algashop.ordering.domain.model.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.model.valueobject.Phone;
import com.algaworks.algashop.ordering.domain.model.valueobject.Recipient;

public class RecipientTestDataBuilder {

    private RecipientTestDataBuilder() {

    }

    public static Recipient.RecipientBuilder aRecipient() {

        return Recipient.builder()
            .document(new Document("newDocto"))
            .fullName(new FullName("fN", "lN"))
            .phone(new Phone("11993044469"))
            ;

    }

}
