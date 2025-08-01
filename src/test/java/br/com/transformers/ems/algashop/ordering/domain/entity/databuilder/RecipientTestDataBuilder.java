package br.com.transformers.ems.algashop.ordering.domain.entity.databuilder;

import br.com.transformers.ems.algashop.ordering.domain.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.FullName;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Phone;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Recipient;

public class RecipientTestDataBuilder {

    private RecipientTestDataBuilder() {

    }

    public static Recipient.RecipientBuilder aRecipient() {

        return Recipient.builder()
            .address(AddressTestDataBuilder.aAddress().build())
            .document(new Document("newDocto"))
            .fullName(new FullName("fN", "lN"))
            .phone(new Phone("11993044469"))
            ;

    }

}
