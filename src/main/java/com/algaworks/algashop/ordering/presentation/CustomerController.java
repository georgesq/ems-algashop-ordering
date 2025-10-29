package com.algaworks.algashop.ordering.presentation;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.application.customer.management.CustomerInput;
import com.algaworks.algashop.ordering.application.customer.query.CustomerOutput;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOutput create(@RequestBody CustomerInput input) {
        AddressData address = input.getAddress();

        return CustomerOutput.builder()
                .id(UUID.randomUUID())
                .phone(input.getPhone())
                .email(input.getEmail())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .birthDate(input.getBirthDate())
                .document(input.getDocument())
                .promotionNotificationsAllowed(input.getPromotionNotificationsAllowed())
                .registeredAt(OffsetDateTime.now())
                .archived(false)
                .loyaltyPoints(0)
                .address(address)
                .build();
    }

}