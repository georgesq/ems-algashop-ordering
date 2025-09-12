package com.algaworks.algashop.ordering.application.customer.management;

import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.customer.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.exception.CantAddLoyaltyPointsOrderIsNotReady;
import com.algaworks.algashop.ordering.domain.model.customer.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.model.customer.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.customer.valueobject.CustomerId;
import com.algaworks.algashop.ordering.domain.model.customer.valueobject.LoyaltyPoints;
import com.algaworks.algashop.ordering.domain.model.order.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.order.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.order.exception.OrderNotBelongsToCustomerException;
import com.algaworks.algashop.ordering.domain.model.order.exception.OrderNotFoundException;
import com.algaworks.algashop.ordering.domain.model.order.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.order.valueobject.OrderId;
import com.algaworks.algashop.ordering.domain.model.product.entity.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.product.service.ProductCatalogService;

@SpringBootTest
@Transactional
public class CustomerLoyaltyPointsApplicationServiceIT {

    @Autowired
    private CustomerLoyaltyPointsApplicationService customerLoyaltyPointsApplicationService;

    @Autowired
    private Customers customers;

    @Autowired
    private Orders orders;

    @MockitoBean
    private ProductCatalogService productCatalogService;

    @BeforeEach
    void setup() {

        var product = ProductTestDataBuilder.aProduct().build();
        Mockito.when(this.productCatalogService.ofId(product.id())).thenReturn(Optional.of(product));

    }

    @Test
    void shouldAddLoyaltyPoints() {

        // arrange
        var customer = CustomerTestDataBuilder.existingCustomer().build();
        this.customers.add(customer);
        var order = OrderTestDataBuilder.anOrder()
                .build();
        order.addItem(ProductTestDataBuilder.aProduct().price(new Money("4500")).build(), new Quantity(2));
        // to ready
        order.place();
        order.markAsPaid();
        order.markAsReady();

        this.orders.add(order);

        // act
        this.customerLoyaltyPointsApplicationService.addLoyaltyPoints(customer.id().value(),
                order.id().value().toString());

        // assert
        var customerChanged = this.customers.ofId(customer.id()).orElseThrow();

        Assertions.assertThat(customerChanged.loyaltyPoints()).isEqualTo(new LoyaltyPoints(75));

    }

    @Test
    void givenInvalidCustomerIdWhenAddLoyaltyPointsThenCustomerNotFoundException() {

        // arrange
        var customerId = new CustomerId(UUID.randomUUID());
        var orderId = new OrderId();

        // assert
        Assertions.assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> this.customerLoyaltyPointsApplicationService.addLoyaltyPoints(customerId.value(),
                        orderId.value().toString()));

    }

    @Test
    void givenInvalidOrderIdWhenAddLoyaltyPointsThenOrderNotFoundException() {

        // arrange
        var customer = CustomerTestDataBuilder.existingCustomer().build();
        this.customers.add(customer);
        var customerId = customer.id();
        var orderId = new OrderId();

        // assert
        Assertions.assertThatExceptionOfType(OrderNotFoundException.class)
                .isThrownBy(() -> this.customerLoyaltyPointsApplicationService.addLoyaltyPoints(customerId.value(),
                        orderId.value().toString()));

    }

    @Test
    void givenArchivedCustomerWhenAddLoyaltyPointsThenCustomerArchivedException() {

        // arrange
        var customer = CustomerTestDataBuilder.existingCustomer()
                .archived(true)
                .build();
        this.customers.add(customer);
        var customerId = customer.id();

        var order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.READY)
                .build();
        this.orders.add(order);
        var orderId = order.id();

        // act

        // assert
        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> {
                    this.customerLoyaltyPointsApplicationService.addLoyaltyPoints(customerId.value(),
                            orderId.value().toString());
                });

    }

    @Test
    void givenTwoCustomerWhenAddLoyaltyPointsToCustomerOrderDifferentThenOrderNotBelongsToCustomerException() {

        // arrange
        var customer1 = CustomerTestDataBuilder.existingCustomer()
                .build();
        this.customers.add(customer1);

        var customer2 = CustomerTestDataBuilder.existingCustomer()
                .id(new CustomerId())
                .build();
        this.customers.add(customer2);

        var order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.READY)
                .customerId(customer2.id())
                .build();
        this.orders.add(order);

        // assert
        Assertions.assertThatExceptionOfType(OrderNotBelongsToCustomerException.class)
                .isThrownBy(() -> {
                    this.customerLoyaltyPointsApplicationService.addLoyaltyPoints(customer1.id().value(),
                            order.id().value().toString());
                });

    }

    @Test
    void givenOrderNotReadyWhenAddLoyaltyPointsThenCantAddLoyaltyPointsOrderIsNotReady() {

        // arrange
        var customer = CustomerTestDataBuilder.existingCustomer()
                .build();
        this.customers.add(customer);

        var order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.DRAFT)
                .customerId(customer.id())
                .build();
        this.orders.add(order);

        // assert
        Assertions.assertThatExceptionOfType(CantAddLoyaltyPointsOrderIsNotReady.class)
                .isThrownBy(() -> {
                    this.customerLoyaltyPointsApplicationService.addLoyaltyPoints(customer.id().value(),
                            order.id().value().toString());
                });

    }

    @Test
    void givenOrderTotalAmountLessThan1000WhenAddLoyaltyPointsThenLoyaltPointsZero() {

        // arrange
        var customer = CustomerTestDataBuilder.existingCustomer()
                .build();
        this.customers.add(customer);

        var order = OrderTestDataBuilder.anOrder()
                .customerId(customer.id())
                .withItems(false)
                .build();
        order.addItem(ProductTestDataBuilder.aProduct().price(new Money("2")).build(), new Quantity(1));
        // to ready
        order.place();
        order.markAsPaid();
        order.markAsReady();

        this.orders.add(order);

        // act
        this.customerLoyaltyPointsApplicationService.addLoyaltyPoints(customer.id().value(),
                order.id().value().toString());

        // assert
        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(0));

    }

}