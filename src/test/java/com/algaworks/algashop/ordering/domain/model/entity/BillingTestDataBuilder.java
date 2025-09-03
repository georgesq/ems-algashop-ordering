package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.Billing;
import com.algaworks.algashop.ordering.domain.model.valueobject.Document;
import com.algaworks.algashop.ordering.domain.model.valueobject.Email;
import com.algaworks.algashop.ordering.domain.model.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.model.valueobject.Phone;

public class BillingTestDataBuilder {

    private BillingTestDataBuilder() {

    }

    public static Billing.BillingBuilder aBilling() {

        return Billing.builder()
                .document(new Document("doct"))
                .fullName(new FullName("fn", "ln"))
                .address(AddressTestDataBuilder.anAddress().build())
                .phone(new Phone("phone"))
                .email(new Email("a@aa.com"))
            ;

    }

}
