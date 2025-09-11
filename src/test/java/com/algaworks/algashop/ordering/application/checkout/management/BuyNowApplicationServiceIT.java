package com.algaworks.algashop.ordering.application.checkout.management;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.application.checkout.databuilder.BuyNowInputTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.customer.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.order.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.order.shipping.service.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.order.shipping.service.ShippingCostService.CalculationRequest;
import com.algaworks.algashop.ordering.domain.model.order.shipping.service.ShippingCostService.CalculationResult;
import com.algaworks.algashop.ordering.domain.model.order.valueobject.OrderId;
import com.algaworks.algashop.ordering.domain.model.product.entity.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.product.service.ProductCatalogService;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.Product;

@SpringBootTest
@Transactional
public class BuyNowApplicationServiceIT {

    @Autowired
    private Customers customers;

    @Autowired
    private Orders orders;

    @Autowired
    private BuyNowApplicationService buyNowApplicationService;

    @MockitoBean
    private ProductCatalogService productCatalogService;

    @MockitoBean
    private ShippingCostService shippingCostService;

    private Product product;

    @BeforeEach
    void setup() {

        if (!this.customers.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
            this.customers.add(CustomerTestDataBuilder.existingCustomer().build());
        }

        this.product = ProductTestDataBuilder.aProduct().build();
        Mockito.when(this.productCatalogService.ofId(product.id())).thenReturn(Optional.of(product));

        Mockito.when(this.shippingCostService.calculate(Mockito.any(CalculationRequest.class))).thenReturn(
            CalculationResult.builder()
            .cost(new Money("10"))
            .expectedDate(LocalDate.now().plusDays(3))
            .build()
        );

    }

    @Test
    void shouldBuyNow() {

        var buyNowInput = BuyNowInputTestDataBuilder.aBuyNowInput().build();

        var orderId = this.buyNowApplicationService.buyNow(buyNowInput);

        Assertions.assertThat(orderId).isNotEmpty();
        Assertions.assertThat(orders.exists(new OrderId(orderId))).isTrue();
        
    }

}
