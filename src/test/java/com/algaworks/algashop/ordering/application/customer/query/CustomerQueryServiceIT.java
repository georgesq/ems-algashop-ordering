package com.algaworks.algashop.ordering.application.customer.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.domain.model.commons.Email;
import com.algaworks.algashop.ordering.domain.model.commons.FullName;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.Customers;

@SpringBootTest
@Transactional
public class CustomerQueryServiceIT {

    @Autowired
    private CustomerQueryService queryService;

    @Autowired
    private Customers customers;

    @Test
    void shouldFilterByName() {

        // arrange
        var customer = CustomerTestDataBuilder.brandNewCustomer().build();
        this.customers.add(customer);
        customer = CustomerTestDataBuilder.brandNewCustomer().fullName(new FullName("otherfn", "otherln")).build();
        this.customers.add(customer);

        CustomerFilter filter = new CustomerFilter(null, customer.fullName().firstName());

        // act
        Page<CustomerSummaryOutput> pageResult = this.queryService.filter(filter);

        // assert
        Assertions.assertThat(pageResult).isNotNull();
        Assertions.assertThat(pageResult.getNumberOfElements()).isEqualTo(1);
        Assertions.assertThat(pageResult.toList().get(0).getFirstName()).isEqualTo(customer.fullName().firstName());

    }

    @Test
    void givenInvalidNameThenEmptyElements() {

        // arrange
        var customer = CustomerTestDataBuilder.brandNewCustomer().build();
        this.customers.add(customer);

        CustomerFilter filter = new CustomerFilter(null, "anyname");

        // act
        Page<CustomerSummaryOutput> pageResult = this.queryService.filter(filter);

        // assert
        Assertions.assertThat(pageResult).isNotNull();
        Assertions.assertThat(pageResult.getNumberOfElements()).isEqualTo(0);

    }

    @Test
    void shouldFilterByEmail() {

        // arrange
        var customer = CustomerTestDataBuilder.brandNewCustomer().build();
        this.customers.add(customer);
        customer = CustomerTestDataBuilder.brandNewCustomer().email(new Email("other@ot.com")).build();
        this.customers.add(customer);

        CustomerFilter filter = new CustomerFilter(customer.email().value(), customer.fullName().firstName());

        // act
        Page<CustomerSummaryOutput> pageResult = this.queryService.filter(filter);

        // assert
        Assertions.assertThat(pageResult).isNotNull();
        Assertions.assertThat(pageResult.getNumberOfElements()).isEqualTo(1);
        Assertions.assertThat(pageResult.toList().get(0).getEmail()).isEqualTo(customer.email().value());

    }

    @Test
    public void shouldFilterByMultipleParams() {
        // arrange
        var customer = CustomerTestDataBuilder.brandNewCustomer().build();
        this.customers.add(customer);
        customer = CustomerTestDataBuilder.brandNewCustomer().email(new Email("other@ot.com")).build();
        this.customers.add(customer);

        CustomerFilter filter = new CustomerFilter(customer.email().value(), customer.fullName().firstName());

        // act
        Page<CustomerSummaryOutput> pageResult = this.queryService.filter(filter);

        // assert
        Assertions.assertThat(pageResult).isNotNull();
        Assertions.assertThat(pageResult.getNumberOfElements()).isEqualTo(1);

    }
    
}
