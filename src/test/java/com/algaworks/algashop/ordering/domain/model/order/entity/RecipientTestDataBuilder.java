package com.algaworks.algashop.ordering.domain.model.order.entity;

import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Document;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Phone;
import com.algaworks.algashop.ordering.domain.model.order.shipping.valueobject.Recipient;

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
