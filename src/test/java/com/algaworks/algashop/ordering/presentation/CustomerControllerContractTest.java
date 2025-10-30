package com.algaworks.algashop.ordering.presentation;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.context.WebApplicationContext;

import com.algaworks.algashop.ordering.application.customer.management.CustomerInput;
import com.algaworks.algashop.ordering.application.customer.management.CustomerManagementApplicationService;
import com.algaworks.algashop.ordering.application.customer.query.CustomerFilter;
import com.algaworks.algashop.ordering.application.customer.query.CustomerOutputTestDataBuilder;
import com.algaworks.algashop.ordering.application.customer.query.CustomerQueryService;
import com.algaworks.algashop.ordering.application.customer.query.CustomerSummaryOutput;
import com.algaworks.algashop.ordering.application.customer.query.CustomerSummaryOutputTestDataBuilder;

import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig;

@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerContractTest {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private CustomerManagementApplicationService applicationService;

    @MockitoBean
    private CustomerQueryService queryService;

    @BeforeEach
    void setupAll() {

        RestAssuredMockMvc.webAppContextSetup(context);
        RestAssuredMockMvc.config = RestAssuredMockMvcConfig.config()
                .encoderConfig(new EncoderConfig(StandardCharsets.UTF_8.name(), StandardCharsets.UTF_8.name()));

        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

    }

    @Test
    void testCreateCustomerContract() {

        // arrange
        String jsonInput = """
                    {
                        "firstName": "John",
                        "lastName": "Doe",
                        "email": "johndoe@email.com",
                        "document": "12345",
                        "phone": "1191234564",
                        "birthDate": "1991-07-05",
                        "promotionNotificationsAllowed": false,
                            "address": {
                                "street": "Bourbon Street",
                                "number": "2000",
                                "complement": "apt 122",
                                "neighborhood": "North Ville",
                                "city": "Yostfort",
                                "state": "South Carolina",
                                "zipCode": "12321"
                            }
                    }
                """;

        var customerOutput = CustomerOutputTestDataBuilder.existing().build();
        UUID customerId = customerOutput.getId();
        Mockito.when(this.applicationService.create(Mockito.any(CustomerInput.class))).thenReturn(customerId);
        Mockito.when(this.queryService.findById(Mockito.any(UUID.class)))
                .thenReturn(customerOutput);

        // assert
        RestAssuredMockMvc
                .given()
                .accept(ContentType.JSON)
                .body(jsonInput)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/v1/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .headers("Location", Matchers.containsString("/api/v1/customers/" + customerId))
                .body(
                        "id", Matchers.equalTo(customerId.toString()),
                        "registeredAt", Matchers.notNullValue(),
                        "firstName", Matchers.is("John"),
                        "lastName", Matchers.is("Doe"),
                        "email", Matchers.is("johndoe@email.com"),
                        "document", Matchers.is("12345"),
                        "phone", Matchers.is("1191234564"),
                        "birthDate", Matchers.is("1991-07-05"),
                        "promotionNotificationsAllowed", Matchers.is(false),
                        "loyaltyPoints", Matchers.is(0),
                        "address.street", Matchers.is("Bourbon Street"),
                        "address.number", Matchers.is("2000"),
                        "address.complement", Matchers.is("apt 122"),
                        "address.neighborhood", Matchers.is("North Ville"),
                        "address.city", Matchers.is("Yostfort"),
                        "address.state", Matchers.is("South Carolina"),
                        "address.zipCode", Matchers.is("12321"));

    }

    @Test
    void createCustomerWithValidationContractError() {

        // arrange
        String jsonInput = """
                    {
                        "firstName": "",
                        "lastName": "",
                        "email": "johndoe@email.com",
                        "document": "12345",
                        "phone": "1191234564",
                        "birthDate": "1991-07-05",
                        "promotionNotificationsAllowed": false,
                            "address": {
                                "street": "Bourbon Street",
                                "number": "2000",
                                "complement": "apt 122",
                                "neighborhood": "North Ville",
                                "city": "Yostfort",
                                "state": "South Carolina",
                                "zipCode": "12321"
                            }
                    }
                """;

        // assert
        RestAssuredMockMvc
                .given()
                .accept(ContentType.JSON)
                .body(jsonInput)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/v1/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(
                        "status", Matchers.equalTo(HttpStatus.BAD_REQUEST.value()),
                        "type", Matchers.equalTo("/errors/invalid-fields"),
                        "title", Matchers.notNullValue(),
                        "detail", Matchers.notNullValue(),
                        "instance", Matchers.notNullValue(),
                        "fields", Matchers.notNullValue());
    }

    @Test
    public void findCustomersContract() {
        int sizeLimit = 5;
        int pageNumber = 0;

        CustomerSummaryOutput customer1 = CustomerSummaryOutputTestDataBuilder.existing().build();
        CustomerSummaryOutput customer2 = CustomerSummaryOutputTestDataBuilder.existingAlt1().build();

        Mockito.when(queryService.filter(Mockito.any(CustomerFilter.class)))
                .thenReturn(new PageImpl<>(List.of(customer1, customer2)));

        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        RestAssuredMockMvc
                .given()
                .accept(ContentType.JSON)
                .queryParam("size", sizeLimit)
                .queryParam("page", pageNumber)
                .when()
                .get("/api/v1/customers")
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value())
                .body(
                        "number", Matchers.equalTo(pageNumber),
                        "size", Matchers.equalTo(2),
                        "totalPages", Matchers.equalTo(1),
                        "totalElements", Matchers.equalTo(2),

                        "content[0].id", Matchers.equalTo(customer1.getId().toString()),
                        "content[0].firstName", Matchers.is(customer1.getFirstName()),
                        "content[0].lastName", Matchers.is(customer1.getLastName()),
                        "content[0].email", Matchers.is(customer1.getEmail()),
                        "content[0].document", Matchers.is(customer1.getDocument()),
                        "content[0].phone", Matchers.is(customer1.getPhone()),
                        "content[0].birthDate", Matchers.is(customer1.getBirthDate().toString()),
                        "content[0].loyaltyPoints", Matchers.is(customer1.getLoyaltyPoints()),
                        "content[0].promotionNotificationsAllowed",
                        Matchers.is(customer1.getPromotionNotificationsAllowed()),
                        "content[0].archived", Matchers.is(customer1.getArchived()),
                        "content[0].registeredAt", Matchers.is(formatter.format(customer1.getRegisteredAt())),

                        "content[1].id", Matchers.equalTo(customer2.getId().toString()),
                        "content[1].firstName", Matchers.is(customer2.getFirstName()),
                        "content[1].lastName", Matchers.is(customer2.getLastName()),
                        "content[1].email", Matchers.is(customer2.getEmail()),
                        "content[1].document", Matchers.is(customer2.getDocument()),
                        "content[1].phone", Matchers.is(customer2.getPhone()),
                        "content[1].birthDate", Matchers.is(customer2.getBirthDate().toString()),
                        "content[1].loyaltyPoints", Matchers.is(customer2.getLoyaltyPoints()),
                        "content[1].promotionNotificationsAllowed",
                        Matchers.is(customer2.getPromotionNotificationsAllowed()),
                        "content[1].archived", Matchers.is(customer2.getArchived()),
                        "content[1].registeredAt", Matchers.is(formatter.format(customer2.getRegisteredAt()))

                );

    }
}
