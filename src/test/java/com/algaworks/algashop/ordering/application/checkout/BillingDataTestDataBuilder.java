package com.algaworks.algashop.ordering.application.checkout;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.application.order.query.BillingData;

public class BillingDataTestDataBuilder {

    public static BillingData.BillingDataBuilder aBillingData() {
        
        return BillingData.builder()
                .firstName("Matt")
                .lastName("Damon")
                .phone("123-321-1112")
                .document("123-45-6789")
                .email("matt.damon@email.com")
                .address(AddressData.builder()
                        .street("Amphitheatre Parkway")
                        .number("1600")
                        .complement("")
                        .neighborhood("Mountain View")
                        .city("Mountain View")
                        .state("California")
                        .zipCode("94043")
                        .build());

    }
}
