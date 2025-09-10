package com.algaworks.algashop.ordering.application.service.customer.management;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.application.management.CustomerInput;
import com.algaworks.algashop.ordering.application.management.CustomerManagementApplicationService;

@SpringBootTest
public class CustomerManagementApplicationServiceIT {

    @Autowired
    private CustomerManagementApplicationService customerManagementApplicationService;

    @Test
    void givenValidCustomerInput_whenCreate_thenPersisted() {

        // arrange
        var input = CustomerInput.builder()
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
                .build())
            .build();

        // act
        var customerId = this.customerManagementApplicationService.create(input);

        // assert
        Assertions.assertThat(customerId).isNotNull();

    }

    @Test
    void givenValidCustomer_whenFindById_thenFindedCustomer() {

        // arrange
        var input = CustomerInput.builder()
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
                .build())
            .build();

        // act
        var customerId = this.customerManagementApplicationService.create(input);

        var customerFinded = this.customerManagementApplicationService.findById(customerId);

        // assert
        Assertions.assertThat(customerFinded).isNotNull();

    }
}
