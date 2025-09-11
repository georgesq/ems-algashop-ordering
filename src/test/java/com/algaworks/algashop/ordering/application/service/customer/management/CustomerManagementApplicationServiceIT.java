package com.algaworks.algashop.ordering.application.service.customer.management;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.application.customer.management.CustomerInput;
import com.algaworks.algashop.ordering.application.customer.management.CustomerManagementApplicationService;
import com.algaworks.algashop.ordering.application.customer.management.CustomerUpdateInput;

@SpringBootTest
@Transactional
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

        Assertions.assertThat(customerFinded.getFirstName()).isEqualTo(input.getFirstName());
        Assertions.assertThat(customerFinded.getLastName()).isEqualTo(input.getLastName());
        Assertions.assertThat(customerFinded.getBirthDate()).isEqualTo(input.getBirthDate());
        Assertions.assertThat(customerFinded.getEmail()).isEqualTo(input.getEmail());
        Assertions.assertThat(customerFinded.getPhone()).isEqualTo(input.getPhone());
        Assertions.assertThat(customerFinded.getDocument()).isEqualTo(input.getDocument()); 
        Assertions.assertThat(customerFinded.getPromotionNotificationsAllowed()).isEqualTo(input.getPromotionNotificationsAllowed());
        Assertions.assertThat(customerFinded.getAddress()).isNotNull();
        Assertions.assertThat(customerFinded.getAddress().getStreet()).isEqualTo(input.getAddress().getStreet());
        Assertions.assertThat(customerFinded.getAddress().getNumber()).isEqualTo(input.getAddress().getNumber());
        Assertions.assertThat(customerFinded.getAddress().getComplement()).isEqualTo(input.getAddress().getComplement());
        Assertions.assertThat(customerFinded.getAddress().getNeighborhood()).isEqualTo(input.getAddress().getNeighborhood());
        Assertions.assertThat(customerFinded.getAddress().getCity()).isEqualTo(input.getAddress().getCity());
        Assertions.assertThat(customerFinded.getAddress().getState()).isEqualTo(input.getAddress().getState());
        Assertions.assertThat(customerFinded.getAddress().getZipCode()).isEqualTo(input.getAddress().getZipCode());

    }

    @Test
    void givenValidCustomerAndUpdateInput_whenUpdate_thenUpdatedCustomer() {

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

        var update = CustomerUpdateInput.builder()
            .firstName("Johnn")
            .lastName("Doer")
            .email("b@a.com")
            .phone("223456789")
            .promotionNotificationsAllowed(true)
            .address(AddressData.builder()
                .street("Streets")
                .number("1234")
                .complement("Apt 12")
                .neighborhood("Neighborhoods")
                .city("Citys")
                .state("States")
                .zipCode("22345")
                .build())
            .build();

        // act
        var customerId = this.customerManagementApplicationService.create(input);

        this.customerManagementApplicationService.update(customerId, update);

        var customerUpdated = this.customerManagementApplicationService.findById(customerId);

        // assert
        Assertions.assertThat(customerUpdated).isNotNull();
        Assertions.assertThat(customerUpdated.getFirstName()).isEqualTo(update.getFirstName());
        Assertions.assertThat(customerUpdated.getLastName()).isEqualTo(update.getLastName());
        Assertions.assertThat(customerUpdated.getEmail()).isEqualTo(update.getEmail());
        Assertions.assertThat(customerUpdated.getPhone()).isEqualTo(update.getPhone());
        Assertions.assertThat(customerUpdated.getPromotionNotificationsAllowed()).isEqualTo(update.getPromotionNotificationsAllowed());
        Assertions.assertThat(customerUpdated.getAddress()).isNotNull();
        Assertions.assertThat(customerUpdated.getAddress().getStreet()).isEqualTo(update.getAddress().getStreet());
        Assertions.assertThat(customerUpdated.getAddress().getNumber()).isEqualTo(update.getAddress().getNumber());
        Assertions.assertThat(customerUpdated.getAddress().getComplement()).isEqualTo(update.getAddress().getComplement());
        Assertions.assertThat(customerUpdated.getAddress().getNeighborhood()).isEqualTo(update.getAddress().getNeighborhood());
        Assertions.assertThat(customerUpdated.getAddress().getCity()).isEqualTo(update.getAddress().getCity());
        Assertions.assertThat(customerUpdated.getAddress().getState()).isEqualTo(update.getAddress().getState());
        Assertions.assertThat(customerUpdated.getAddress().getZipCode()).isEqualTo(update.getAddress().getZipCode());

    }

}
