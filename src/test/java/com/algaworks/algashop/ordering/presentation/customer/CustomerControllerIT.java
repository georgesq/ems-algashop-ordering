package com.algaworks.algashop.ordering.presentation.customer;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.config.JsonConfig.jsonConfig;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.algaworks.algashop.ordering.application.customer.management.CustomerInputTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityRepository;
import com.github.tomakehurst.wiremock.WireMockServer;

import io.restassured.RestAssured;
import io.restassured.path.json.config.JsonPathConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerControllerIT {

    @LocalServerPort
    private int port;

    private WireMockServer wireMockCustomer;

    @Autowired
    private CustomerPersistenceEntityRepository customerRepository;

    @BeforeEach
    public void setup() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;

        RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));

        initDatabase();

        wireMockCustomer = new WireMockServer(options()
                .port(8780));

        wireMockCustomer.start();

    }

    @AfterEach
    public void after() {
        wireMockCustomer.stop();
    }

    private void initDatabase() {

    }

    @Test
    void shouldCreateAnCustomer() {

        // arrange
        var payload = CustomerInputTestDataBuilder.aCustomer().build();

        // act
        String createdCustomerId = RestAssured
                .given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType("application/json")
                    .body(payload)
                .when()
                    .post("/api/v1/customers")
                .then()
                    .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                    .jsonPath().getString("id");

        // assert
        Assertions.assertThat(createdCustomerId).isNotBlank();

        var createdCustomer = customerRepository.findById(UUID.fromString(createdCustomerId.strip()))
                .orElseThrow(() -> new AssertionError("Customer not found in database"));

        Assertions.assertThat(createdCustomer.getLastName()).isEqualTo(payload.getLastName());
        Assertions.assertThat(createdCustomer.getFirstName()).isEqualTo(payload.getFirstName());

    }

    @Test
    void shouldNotCreateAnCustomerWhenInvalidData() {

        // arrange
        var payload = CustomerInputTestDataBuilder.aCustomer().build();
        payload.setFirstName(null);

        // act
        RestAssured
                .given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType("application/json")
                    .body(payload)
                .when()
                    .post("/api/v1/customers")
                .then()
                    .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);

        // assert

    }

    @Test
    void shouldDeleteAnCustomer() {

        // arrange
        var payload = CustomerInputTestDataBuilder.aCustomer().build();

        String customerId = RestAssured
                .given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType("application/json")
                    .body(payload)
                .when()
                    .post("/api/v1/customers")
                .then()
                    .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                    .jsonPath().getString("id");

        // act
        RestAssured
                .given()
                    .pathParam("customerId", customerId)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType("application/json")
                .when()
                    .delete("/api/v1/customers/{customerId}")
                .then()
                    .assertThat()
                        .statusCode(HttpStatus.NO_CONTENT.value());

        // assert
        var deletedCustomer = customerRepository.findById(UUID.fromString(customerId.strip()))
                .orElseThrow(() -> new AssertionError("Customer not found in database"));

        Assertions.assertThat(deletedCustomer.getArchived()).isTrue();

    }

    @Test
    void shouldNotDeleteAnNotFoundCustomer() {

        // arrange

        // act
        RestAssured
                .given()
                    .pathParam("customerId", "6e148bd5-47f6-4022-b9da-07cfaa294f7a")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType("application/json")
                .when()
                    .delete("/api/v1/customers/{customerId}")
                .then()
                    .assertThat()
                        .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
}
