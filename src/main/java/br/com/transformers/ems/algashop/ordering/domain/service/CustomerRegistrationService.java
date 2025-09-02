package br.com.transformers.ems.algashop.ordering.domain.service;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.model.repository.Customers;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Address;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.BirthDate;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.FullName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Phone;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerRegistrationService {
    
    private Customers customers;

    public Customer register(

        FullName fullName,
        BirthDate birthDate,
        Email email,
        Phone phone,
        Document document,
        Boolean promotionNotificationsAllowed,
        Address address

    ) {

        Customer customer = Customer.brandNew()
            .fullName(fullName)
            .birthDate(birthDate)
            .email(email)
            .phone(phone)
            .document(document)
            .promotionNotificationsAllowed(promotionNotificationsAllowed)
            .address(address)
        .build();

        this.verifyEmailUnique(email, customer.id());

        return customer;

    }

    public void changeEmail(Customer customer, Email newEmail) {

        this.verifyEmailUnique(newEmail, customer.id());
        
        customer.changeEmail(newEmail);

    }

    private void verifyEmailUnique(Email email, CustomerId id) {

        if (!this.customers.isEmailUnique(email, id)) {
            throw new IllegalArgumentException("Email must be unique");
        }

    }

}
