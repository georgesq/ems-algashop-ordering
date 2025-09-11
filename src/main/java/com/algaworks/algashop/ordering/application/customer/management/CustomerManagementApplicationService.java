package com.algaworks.algashop.ordering.application.customer.management;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.application.utility.Mapper;
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

    private final Mapper mapper;
    
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

        return this.mapper.convert(customer, CustomerOutput.class);
        
    }   

    @Transactional
    public void update(UUID customerId, CustomerUpdateInput input) {

        Objects.requireNonNull(customerId);
        Objects.requireNonNull(input);

        var customer = this.customers.ofId(new CustomerId(customerId))
            .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));

        if (input.getFirstName() != null && input.getLastName() != null) {
            customer.changeName(new FullName(input.getFirstName(), input.getLastName()));
        }

        if (input.getEmail() != null) {
            customer.changeEmail(new Email(input.getEmail()));
        }

        if (input.getPhone() != null) {
            customer.changePhone(new Phone(input.getPhone()));
        }

        if (input.getPromotionNotificationsAllowed() != null) {

            if (input.getPromotionNotificationsAllowed()) {
                customer.enablePromotionNotifications();
            } else {
                customer.disablePromotionNotifications();   
            }

        }

        if (input.getAddress() != null) {
            var addressData = input.getAddress();
            customer.changeAddress(new Address(
                addressData.getStreet(), 
                addressData.getComplement(), 
                addressData.getNeighborhood(), 
                addressData.getNumber(), 
                addressData.getCity(), 
                addressData.getState(), 
                new ZipCode(addressData.getZipCode()))
            );
        }

        this.customers.add(customer);

    }
}