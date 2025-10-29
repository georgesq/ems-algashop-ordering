package com.algaworks.algashop.ordering.presentation;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.context.WebApplicationContext;

import com.algaworks.algashop.ordering.application.customer.management.CustomerInput;
import com.algaworks.algashop.ordering.application.customer.management.CustomerManagementApplicationService;
import com.algaworks.algashop.ordering.application.customer.query.CustomerOutputTestDataBuilder;
import com.algaworks.algashop.ordering.application.customer.query.CustomerQueryService;

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

}
