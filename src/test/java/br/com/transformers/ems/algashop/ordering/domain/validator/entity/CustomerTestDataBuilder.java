package br.com.transformers.ems.algashop.ordering.domain.validator.entity;

import br.com.transformers.ems.algashop.ordering.domain.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.*;

import java.time.LocalDate;

public class CustomerTestDataBuilder {
    private CustomerTestDataBuilder() {
    }

    public static Customer.NewSimpleCustomerBuild newInstance() {
        return Customer.newInstance()
                .fullName(new FullName("fn", "ln"))
                .birthDate(new BirthDate(LocalDate.ofYearDay(LocalDate.now().getYear() - 1, 1)))
                .email(new Email("email@email.com"))
                .phone(new Phone("11993044469"))
                .document(new Document("57044961568"))
                .promotionNotificaficationsAllowed(Boolean.FALSE)
                .address(Address.builder()
                        .street("st")
                        .number(1)
                        .complement("cp")
                        .neighborhood("nb")
                        .city("ct")
                        .state("st")
                        .zipCode(new ZipCode("41710"))
                        .build());
    }
}
