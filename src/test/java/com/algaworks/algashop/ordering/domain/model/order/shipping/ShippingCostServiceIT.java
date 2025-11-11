package com.algaworks.algashop.ordering.domain.model.order.shipping;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.config.JsonConfig.jsonConfig;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import com.algaworks.algashop.ordering.domain.model.commons.ZipCode;
import com.algaworks.algashop.ordering.domain.model.order.shipping.ShippingCostService.CalculationRequest;
import com.github.tomakehurst.wiremock.WireMockServer;

import io.restassured.RestAssured;
import io.restassured.path.json.config.JsonPathConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ShippingCostServiceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ShippingCostService shippingCostService;

    @Autowired
    private OriginAddressService originAddressService;

    private WireMockServer wireMockRapidex;

    @BeforeEach
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;

        RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));

        wireMockRapidex = new WireMockServer(options()
                .port(8780)
                .usingFilesUnderDirectory("src/test/resources/wiremock/rapidex"));

        wireMockRapidex.start();

    }

    @AfterEach
    public void after() {

        wireMockRapidex.stop();

    }

    @Test
    void shouldCalculate() {

        ZipCode origin = originAddressService.originAddress().zipCode();
        ZipCode destination = new ZipCode("12345");

        var calculate = shippingCostService
                .calculate(new CalculationRequest(origin, destination));

        Assertions.assertThat(calculate.cost()).isNotNull();
        Assertions.assertThat(calculate.expectedDate()).isNotNull();

    }

}