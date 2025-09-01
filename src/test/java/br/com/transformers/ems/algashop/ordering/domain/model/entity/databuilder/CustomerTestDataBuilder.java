package br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Address;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.BirthDate;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.FullName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.LoyaltyPoints;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Phone;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ZipCode;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;

public class CustomerTestDataBuilder {

    public static final CustomerId DEFAULT_CUSTOMER_ID = new CustomerId();

    private CustomerTestDataBuilder() {
    }

    public static Customer.BrandNewCustomerBuild brandNewCustomer() {
        return Customer.brandNew()
                .fullName(new FullName("John","Doe"))
                .birthDate(new BirthDate(LocalDate.of(1991, 7,5)))
                .email(new Email("johndoe@email.com"))
                .phone(new Phone("478-256-2604"))
                .document(new Document("255-08-0578"))
                .promotionNotificationsAllowed(true)
                .address(Address.builder()
                        .street("Bourbon Street")
                        .number(1134)
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build());
    }

    public static Customer.ExistingCustomerBuild existingCustomer() {
        return Customer.existing()
                .id(DEFAULT_CUSTOMER_ID)
                .registeredAt(OffsetDateTime.now())
                .promotionNotificationsAllowed(true)
                .archived(false)
                .archivedAt(null)
                .fullName(new FullName("John","Doe"))
                .birthDate(new BirthDate(LocalDate.of(1991, 7,5)))
                .email(new Email("johndoe@email.com"))
                .phone(new Phone("478-256-2604"))
                .document(new Document("255-08-0578"))
                .promotionNotificationsAllowed(true)
                .loyaltyPoints(LoyaltyPoints.ZERO)
                .address(Address.builder()
                        .street("Bourbon Street")
                        .number(1134)
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build())
                ;
    }

    public static Customer.ExistingCustomerBuild existingAnonymizedCustomer() {
        return Customer.existing()
                .id(new CustomerId())
                .fullName(new FullName("Anonymous", "Anonymous"))
                .birthDate(null)
                .email(new Email("anonymous@anonymous.com"))
                .phone(new Phone("000-000-0000"))
                .document(new Document("000-00-0000"))
                .promotionNotificationsAllowed(false)
                .archived(true)
                .registeredAt(OffsetDateTime.now())
                .archivedAt(OffsetDateTime.now())
                .loyaltyPoints(new LoyaltyPoints(10))
                .address(Address.builder()
                        .street("Bourbon Street")
                        .number(1134)
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build());
    }
}