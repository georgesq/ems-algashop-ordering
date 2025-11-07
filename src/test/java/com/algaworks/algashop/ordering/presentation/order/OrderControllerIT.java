package com.algaworks.algashop.ordering.presentation.order;

import static io.restassured.config.JsonConfig.jsonConfig;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.utils.AlgaShopResourceUtils;
import com.github.tomakehurst.wiremock.WireMockServer;

import io.restassured.RestAssured;
import io.restassured.path.json.config.JsonPathConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock({
    @ConfigureWireMock(
        name = "product-catalog", 
        port = 8780,
        filesUnderDirectory = "src/test/resources/wiremock/product-catalog"
    ),
    @ConfigureWireMock(
        name = "rapidex", 
        port = 8782,
        filesUnderDirectory = "src/test/resources/wiremock/rapidex"
    ),
})
public class OrderControllerIT {

    @LocalServerPort
    private int port;

    private static boolean databaseInitialized;

    @Autowired
    private CustomerPersistenceEntityRepository customerRepository;

    private static final UUID validCustomerId = UUID.fromString("6e148bd5-47f6-4022-b9da-07cfaa294f7a");

    @InjectWireMock("product-catalog")
    private WireMockServer wireMockProductCatalog;
    @InjectWireMock("rapidex")
    private WireMockServer wireMockRapidex;

    @BeforeEach
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;

        RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));

        initDatabase();

    }

    @SuppressWarnings("null")
    private void initDatabase() {
        if (databaseInitialized) {
            return;
        }

        customerRepository.saveAndFlush(
                CustomerPersistenceEntityTestDataBuilder.aCustomer().id(validCustomerId).build());

        databaseInitialized = true;
    }

    @Test
    public void shouldCreateOrderUsingProduct() {
        System.out.println("Stub mapping size: " + wireMockProductCatalog.getStubMappings().size());

        String json = AlgaShopResourceUtils.readContent("json/create-order-with-product.json");
        RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(json)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    public void shouldCNotreateOrderUsingProductWhenCustomerNotFound() {

        String json = AlgaShopResourceUtils.readContent("json/create-order-with-product-and-invalid-customer.json");
        RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(json)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    public void shouldNotCreateOrderUsingProductWhenProductAPIUnavailable() {

        // arrange
        String json = AlgaShopResourceUtils.readContent("json/create-order-with-product.json");
        this.wireMockProductCatalog.stop();

        // act / assert
        RestAssured
                .given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType("application/vnd.order-with-product.v1+json")
                    .body(json)
                .when()
                    .post("/api/v1/orders")
                .then()
                    .assertThat()
                        .statusCode(HttpStatus.BAD_GATEWAY.value())
                        .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
    }

    @Test
    public void shouldNotCreateOrderUsingProductWhenProductNotExists() {

        // arrange
        String json = AlgaShopResourceUtils.readContent("json/create-order-with-invalid-product.json");

        // act / assert
        RestAssured
                .given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType("application/vnd.order-with-product.v1+json")
                    .body(json)
                .when()
                    .post("/api/v1/orders")
                .then()
                    .assertThat()
                        .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
    }

}
