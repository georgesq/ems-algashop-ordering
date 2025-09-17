package com.algaworks.algashop.ordering.application.checkout;

import com.algaworks.algashop.ordering.application.commons.AddressData;

public class ShippingInputTestDataBuilder {

    public static ShippingInput.ShippingInputBuilder aShippingInput() {
        
        return ShippingInput.builder()
                .recipient(RecipientData.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .document("255-08-0578")
                        .phone("478-256-2604")
                        .build())
                .address(AddressData.builder()
                        .street("Elm Street")
                        .number("456")
                        .complement("House A")
                        .neighborhood("Central Park")
                        .city("Springfield")
                        .state("Illinois")
                        .zipCode("62704")
                        .build());

    }

}
