package com.algaworks.algashop.ordering.domain.model.order.entity;

import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Document;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Email;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Phone;
import com.algaworks.algashop.ordering.domain.model.order.shipping.valueobject.Billing;

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
