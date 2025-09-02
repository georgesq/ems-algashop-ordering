package br.com.transformers.ems.algashop.ordering.domain.service;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.model.repository.Customers;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Address;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.BirthDate;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.FullName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Phone;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ZipCode;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;

@ExtendWith(MockitoExtension.class)
class CustomerRegistrationServiceTest {

    @Mock
    private Customers customers;

    @InjectMocks
    private CustomerRegistrationService service;

    @BeforeEach
    void setup() {

        when(customers.isEmailUnique(Mockito.any(Email.class), Mockito.any(CustomerId.class)))
            .thenReturn(true);  

    }

    @Test
    void givenCorrectParameters_whenRegisterCustomer_thenShouldRegisterCustomer() {

        Customer customer = this.service.register(
                new FullName("John","Doe"),
                new BirthDate(LocalDate.of(1991, 7,5)),
                new Email("johndoe@email.com"),
                new Phone("478-256-2604"),
                new Document("255-08-0578"),
                true,
                Address.builder()
                        .street("Bourbon Street")
                        .number(1134)
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build()
        );

        Assertions.assertThat(customer).isNotNull();
        Assertions.assertThat(customer.phone()).isEqualTo(new Phone("478-256-2604"));

    }   

}
