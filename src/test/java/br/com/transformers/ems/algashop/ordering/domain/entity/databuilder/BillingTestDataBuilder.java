package br.com.transformers.ems.algashop.ordering.domain.entity.databuilder;

import br.com.transformers.ems.algashop.ordering.domain.valueobject.Billing;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.FullName;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Phone;

public class BillingTestDataBuilder {

    private BillingTestDataBuilder() {

    }

    public static Billing.BillingBuilder aBilling() {

        return Billing.builder()
                .document(new Document("doct"))
                .fullName(new FullName("fn", "ln"))
                .address(AddressTestDataBuilder.aAddress().build())
                .phone(new Phone("phone"))
                .email(new Email("a@aa.com"))
            ;

    }

}
