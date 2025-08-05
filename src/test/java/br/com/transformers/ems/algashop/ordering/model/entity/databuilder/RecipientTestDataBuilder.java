package br.com.transformers.ems.algashop.ordering.model.entity.databuilder;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.FullName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Phone;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Recipient;

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
