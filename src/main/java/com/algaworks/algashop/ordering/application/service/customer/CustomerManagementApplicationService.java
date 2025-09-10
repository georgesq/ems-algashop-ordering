package com.algaworks.algashop.ordering.application.service.customer;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.application.model.customer.CustomerInput;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Address;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Document;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Email;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Phone;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.ZipCode;
import com.algaworks.algashop.ordering.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.customer.service.CustomerRegistrationService;
import com.algaworks.algashop.ordering.domain.model.customer.valueobject.BirthDate;

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

}