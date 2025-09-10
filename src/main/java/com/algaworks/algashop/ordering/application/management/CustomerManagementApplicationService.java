package com.algaworks.algashop.ordering.application.management;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Address;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Document;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Email;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Phone;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.ZipCode;
import com.algaworks.algashop.ordering.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.customer.service.CustomerRegistrationService;
import com.algaworks.algashop.ordering.domain.model.customer.valueobject.BirthDate;
import com.algaworks.algashop.ordering.domain.model.customer.valueobject.CustomerId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerManagementApplicationService {

    private final CustomerRegistrationService customerRegistrationService;
    private final Customers customers;
    
    @Transactional
    public UUID create(CustomerInput input) {

        Objects.requireNonNull(input);

        var customer = this.customerRegistrationService.register(
            new FullName(input.getFirstName(), input.getLastName()), 
            new BirthDate(input.getBirthDate()), 
            new Email(input.getEmail()), 
            new Phone(input.getPhone()), 
            new Document(input.getDocument()), 
            input.getPromotionNotificationsAllowed(), 
            new Address(
                input.getAddress().getStreet(), 
                input.getAddress().getComplement(), 
                input.getAddress().getNeighborhood(), 
                input.getAddress().getNumber(), 
                input.getAddress().getCity(), 
                input.getAddress().getState(), 
                new ZipCode(input.getAddress().getZipCode()))
        );

        this.customers.add(customer);

        return customer.id().value();

    }

    @Transactional(readOnly = true)
    public CustomerOutput findById(UUID customerId) {

        var customer = this.customers.ofId(new CustomerId(customerId))
            .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));

        return CustomerOutput.builder()
            .firstName(customer.fullName().firstName())
            .lastName(customer.fullName().lastName())
            .email(customer.email().value())
            .phone(customer.phone().value())
            .document(customer.document().value())
            .birthDate(customer.birthDate().value())
            .promotionNotificationsAllowed(customer.isPromotionNotificationsAllowed())
            .loyaltyPoints(customer.loyaltyPoints().value())
            .registeredAt(customer.registeredAt())
            .archivedAt(customer.archivedAt())
            .address(AddressData.builder()
                .street(customer.address().street())
                .number(customer.address().number())
                .complement(customer.address().complement())
                .neighborhood(customer.address().neighborhood())
                .city(customer.address().city())
                .state(customer.address().state())
                .zipCode(customer.address().zipCode().value())
                .build())
            .build();

    }   

}