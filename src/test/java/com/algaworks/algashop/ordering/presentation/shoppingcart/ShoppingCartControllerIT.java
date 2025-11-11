package com.algaworks.algashop.ordering.presentation.shoppingcart;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.config.JsonConfig.jsonConfig;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.algaworks.algashop.ordering.application.shoppingcart.management.ShoppingCartInputTestDataBuilder;
import com.algaworks.algashop.ordering.application.shoppingcart.management.ShoppingCartItemInput;
import com.algaworks.algashop.ordering.application.shoppingcart.query.ShoppingCartOutput;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.ShoppingCartPersistenceEntityRepository;
import com.github.tomakehurst.wiremock.WireMockServer;

import io.restassured.RestAssured;
import io.restassured.path.json.config.JsonPathConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ShoppingCartControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private CustomerPersistenceEntityRepository customerRepository;

    @Autowired
    private ShoppingCartPersistenceEntityRepository shoppingCartRepository;

    private WireMockServer wireMockProductCatalog;

    private static final UUID VALID_CUSTOMER_ID = UUID.fromString("4f229073-2e69-4479-ba24-9a47aa98cfc5");
    private static final UUID VALID_PRODUCT_ID = UUID.fromString("fffe6ec2-7103-48b3-8e4f-3b58e43fb75a");

    @BeforeEach
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;

        RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));

        initDatabase();

        wireMockProductCatalog = new WireMockServer(options()
                .port(8781)
                .usingFilesUnderDirectory("src/test/resources/wiremock/product-catalog"));

        wireMockProductCatalog.start();

    }

    @AfterEach
    public void after() {

        wireMockProductCatalog.stop();
        
    }

    private void initDatabase() {

        customerRepository.saveAndFlush(
                CustomerPersistenceEntityTestDataBuilder.aCustomer().id(VALID_CUSTOMER_ID).build());

    }

    @Test
    public void shouldCreateOrderUsingProduct() {

        var payload = ShoppingCartInputTestDataBuilder.aShoppingCart(VALID_CUSTOMER_ID);

        var shoppingCartId = RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/api/v1/shopping-carts")
                .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.not(Matchers.emptyString()),
                        "customerId", Matchers.is(VALID_CUSTOMER_ID.toString()))
                .extract()
                .jsonPath().getString("id");

        boolean orderExists = this.shoppingCartRepository.existsById(UUID.fromString(shoppingCartId));

        Assertions.assertThat(orderExists).isTrue();

    }

    @Test
    public void shouldNotCreateShoppingCartWhenCustomerNotExists() {

        var payload = ShoppingCartInputTestDataBuilder.aShoppingCart(VALID_CUSTOMER_ID);
        payload.setCustomerId(null);

        RestAssured
                .given()
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType("application/json")
                        .body(payload)
                .when()
                        .post("/api/v1/shopping-carts")
                .then()
                        .assertThat()
                        .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void shouldAddProduct() {

        // arrange

        var payload = ShoppingCartInputTestDataBuilder.aShoppingCart(VALID_CUSTOMER_ID);

        var shoppingCartId = RestAssured
                .given()
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType("application/json")
                        .body(payload)
                .when()
                        .post("/api/v1/shopping-carts")
                .then()
                        .assertThat()
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .statusCode(HttpStatus.CREATED.value())
                .extract()
                        .jsonPath().getString("id");

        var scii = ShoppingCartItemInput.builder()
                .shoppingCartId(UUID.fromString(shoppingCartId))
                .productId(VALID_PRODUCT_ID)
                .quantity(2).build();

        // action
        RestAssured
                .given()
                        .pathParam("shoppingCartId", shoppingCartId)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType("application/json")
                        .body(scii)
                .when()
                        .post("/api/v1/shopping-carts/{shoppingCartId}/items")
                .then()
                        .assertThat()
                                .statusCode(HttpStatus.NO_CONTENT.value());

        // asserts
        var shoppingCart = RestAssured
                .given()
                        .pathParam("shoppingCartId", shoppingCartId)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType("application/json")
                        .body(scii)
                .when()
                        .get("/api/v1/shopping-carts/{shoppingCartId}")
                .then()
                        .extract()
                                .as(ShoppingCartOutput.class);


        Assertions.assertThat(shoppingCart.getItems()).isNotEmpty();
        Assertions.assertThat(shoppingCart.getTotalItems()).isEqualTo(2);

        shoppingCart.getItems().forEach((e) -> {
            Assertions.assertThat(e.getProductId()).isEqualTo(VALID_PRODUCT_ID);
            Assertions.assertThat(e.getQuantity()).isEqualTo(scii.getQuantity());
        });
        

    }

}
