package com.algaworks.algashop.ordering.application.customer.databuilder;

import java.time.LocalDate;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.application.customer.management.CustomerInput;

public class CustomerInputTestDataBuilder {
    
    public static CustomerInput.CustomerInputBuilder aCustomerInput() {
        
        return CustomerInput.builder()
            .firstName("John")
            .lastName("Doe")
            .birthDate(LocalDate.now().minusYears(10))
            .email("a@a.com")
            .phone("123456789")
            .document("doc123")
            .promotionNotificationsAllowed(true)
            .address(AddressData.builder()
                .street("Street")
                .number("123")
                .complement("Apt 1")
                .neighborhood("Neighborhood")
                .city("City")
                .state("State")
                .zipCode("12345")
                .build());

    }

}
