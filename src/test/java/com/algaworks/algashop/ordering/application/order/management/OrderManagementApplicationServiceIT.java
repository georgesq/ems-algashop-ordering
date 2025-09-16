package com.algaworks.algashop.ordering.application.order.management;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.Customers;
import com.algaworks.algashop.ordering.domain.model.order.OrderId;
import com.algaworks.algashop.ordering.domain.model.order.OrderNotFoundException;
import com.algaworks.algashop.ordering.domain.model.order.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.order.OrderStatusCannotBeChangedException;
import com.algaworks.algashop.ordering.domain.model.order.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.order.Orders;

@SpringBootTest
@Transactional
public class OrderManagementApplicationServiceIT {

    @Autowired
    private OrderManagementApplicationService orderManagementApplicationService;

    @Autowired
    private Orders orders;

    @Autowired
    private Customers customers;

    @Test
    void shouldCancel() {

        // arrange
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        var order = OrderTestDataBuilder.anOrder()
            .customerId(customer.id())
            .build();
        Long rawOrderId = order.id().value().toLong();

        this.orders.add(order);

        // act
        this.orderManagementApplicationService.cancel(rawOrderId);

        // assert
        var canceled = this.orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(canceled.isCanceled()).isTrue();
        
    }

    @Test
    void givenInvalidOrderIdWhenCancelThenOrderNotFoundException() {

        // arrange 
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        var order = OrderTestDataBuilder.anOrder()
            .customerId(customer.id())
            .build();

        this.orders.add(order);

        // assert
        Assertions.assertThatExceptionOfType(OrderNotFoundException.class)
                .isThrownBy(() -> this.orderManagementApplicationService.cancel(new OrderId().value().toLong()));

    }

    @Test
    void givenOrderCancelledWhenCancelThenError() {

        // arrange 
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        var order = OrderTestDataBuilder.anOrder()
            .customerId(customer.id())
            .status(OrderStatus.CANCELED)
            .build();

        this.orders.add(order);

        // assert
        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(() -> this.orderManagementApplicationService.cancel(order.id().value().toLong()));
        
    }

    @Test
    void shouldMarkAsPaid() {

        // arrange
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        var order = OrderTestDataBuilder.anOrder()
            .status(OrderStatus.PLACED)
            .customerId(customer.id())
            .build();
        Long rawOrderId = order.id().value().toLong();

        this.orders.add(order);

        // act
        this.orderManagementApplicationService.markAsPaid(rawOrderId);

        // assert
        var canceled = this.orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(canceled.isPaid()).isTrue();
        
    }

    @Test
    void givenInvalidOrderIdWhenMarAsPaidThenOrderNotFoundException() {

        // arrange 
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        var order = OrderTestDataBuilder.anOrder()
            .customerId(customer.id())
            .build();

        this.orders.add(order);

        // assert
        Assertions.assertThatExceptionOfType(OrderNotFoundException.class)
                .isThrownBy(() -> this.orderManagementApplicationService.markAsPaid(new OrderId().value().toLong()));

    }

    @Test
    void givenOrderPaiedWhenCancelThenError() {

        // arrange 
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        var order = OrderTestDataBuilder.anOrder()
            .customerId(customer.id())
            .status(OrderStatus.PAID)
            .build();

        this.orders.add(order);

        // assert
        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(() -> this.orderManagementApplicationService.markAsPaid(order.id().value().toLong()));
        
    }

    @Test
    void shouldMarkAsReady() {

        // arrange
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        var order = OrderTestDataBuilder.anOrder()
            .status(OrderStatus.PAID)
            .customerId(customer.id())
            .build();
        Long rawOrderId = order.id().value().toLong();

        this.orders.add(order);

        // act
        this.orderManagementApplicationService.markAsReady(rawOrderId);

        // assert
        var changed = this.orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(changed.isReady()).isTrue();
        
    }

    @Test
    void givenInvalidOrderIdWhenMarAsReadyThenOrderNotFoundException() {

        // arrange 
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        var order = OrderTestDataBuilder.anOrder()
            .customerId(customer.id())
            .build();

        this.orders.add(order);

        // assert
        Assertions.assertThatExceptionOfType(OrderNotFoundException.class)
                .isThrownBy(() -> this.orderManagementApplicationService.markAsReady(new OrderId().value().toLong()));

    }

    @Test
    void givenOrderReadyWhenCancelThenOrderStatusCannotBeChangedException() {

        // arrange 
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        var order = OrderTestDataBuilder.anOrder()
            .customerId(customer.id())
            .status(OrderStatus.PAID)
            .build();

        this.orders.add(order);

        // assert
        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(() -> this.orderManagementApplicationService.markAsReady(order.id().value().toLong()));
        
    }

}
