package com.algaworks.algashop.ordering.application.order.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.Customers;
import com.algaworks.algashop.ordering.domain.model.order.Order;
import com.algaworks.algashop.ordering.domain.model.order.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.order.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.order.Orders;

@SpringBootTest
@Transactional
public class OrderQueryServiceIT {

    @Autowired
    private OrderQueryService queryService;

    @Autowired
    private Orders orders;

    @Autowired
    private Customers customers;

    @Test
    void shouldFindById() {

        // arrange
        var customer = CustomerTestDataBuilder.brandNewCustomer().build();
        this.customers.add(customer);

        Order order = OrderTestDataBuilder.anOrder().customerId(customer.id()).build();
        this.orders.add(order);

        // act
        var output = this.queryService.findById(order.id().toString());

        // assert
        Assertions.assertThat(output)
                .extracting(
                        OrderDetailOutput::getId,
                        OrderDetailOutput::getTotalAmount)
                .containsExactly(
                        order.id().toString(),
                        order.totalAmount().value());

    }

    @Test
    void shouldFilterPage() {

        // arranje
        var customer = CustomerTestDataBuilder.brandNewCustomer().build();
        this.customers.add(customer);

        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).withItems(false)
                .customerId(customer.id()).build());
        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).customerId(customer.id()).build());
        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).customerId(customer.id()).build());
        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.READY).customerId(customer.id()).build());
        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).customerId(customer.id()).build());

        // act
        Page<OrderSummaryOutput> output = this.queryService.filter(new OrderFilter(2, 0));
        ;

        // assert
        Assertions.assertThat(output).isNotNull();
        Assertions.assertThat(output.getSize()).isEqualTo(2l);

    }

    @Test
    void shouldFilterPageByCustomerId() {

        // arrange
        var customer1 = CustomerTestDataBuilder.existingCustomer().build();
        this.customers.add(customer1);

        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).withItems(false)
                .customerId(customer1.id()).build());
        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).customerId(customer1.id()).build());

        var customer2 = CustomerTestDataBuilder.existingCustomer().id(new CustomerId()).build();
        this.customers.add(customer2);

        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).customerId(customer2.id()).build());
        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.READY).customerId(customer2.id()).build());
        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).customerId(customer2.id()).build());

        OrderFilter filter = new OrderFilter();
        filter.setCustomerId(customer1.id().value());

        // act
        Page<OrderSummaryOutput> page = this.queryService.filter(filter);

        // assert
        Assertions.assertThat(page).isNotNull();
        Assertions.assertThat(page.getSize()).isEqualTo(2l);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(2);
        Assertions.assertThat(page.getNumberOfElements()).isEqualTo(2);
    }

    @Test
    public void shouldFilterByMultipleParams() {

        Customer customer1 = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(customer1);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).withItems(false).customerId(customer1.id())
                .build());
        Order order1 = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).customerId(customer1.id()).build();
        orders.add(order1);

        Customer customer2 = CustomerTestDataBuilder.existingCustomer().id(new CustomerId()).build();
        customers.add(customer2);
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).customerId(customer2.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.READY).customerId(customer2.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).customerId(customer2.id()).build());

        OrderFilter filter = new OrderFilter();
        filter.setCustomerId(customer1.id().value());
        filter.setStatus(OrderStatus.PLACED.toString().toLowerCase());
        filter.setTotalAmountFrom(order1.totalAmount().value());

        Page<OrderSummaryOutput> page = queryService.filter(filter);

        Assertions.assertThat(page.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(1);
        Assertions.assertThat(page.getNumberOfElements()).isEqualTo(1);

    }

    @Test
    public void givenInvalidOrderId_whenFilter_shouldReturnEmptyPage() {
        Customer customer1 = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(customer1);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).withItems(false).customerId(customer1.id())
                .build());
        Order order1 = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).customerId(customer1.id()).build();
        orders.add(order1);

        Customer customer2 = CustomerTestDataBuilder.existingCustomer().id(new CustomerId()).build();
        customers.add(customer2);
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).customerId(customer2.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.READY).customerId(customer2.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).customerId(customer2.id()).build());

        OrderFilter filter = new OrderFilter();
        filter.setOrderId("ABC");

        Page<OrderSummaryOutput> page = queryService.filter(filter);

        Assertions.assertThat(page.getTotalPages()).isEqualTo(0);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(0);
        Assertions.assertThat(page.getNumberOfElements()).isEqualTo(0);
    }

    @Test
    public void shouldOrderByStatus() {

        // arrange
        Customer customer1 = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(customer1);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).withItems(false).customerId(customer1.id())
                .build());
        Order order1 = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).customerId(customer1.id()).build();
        orders.add(order1);

        Customer customer2 = CustomerTestDataBuilder.existingCustomer().id(new CustomerId()).build();
        customers.add(customer2);
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).customerId(customer2.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.READY).customerId(customer2.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).customerId(customer2.id()).build());

        OrderFilter filter = new OrderFilter();
        filter.setSortByProperty(OrderFilter.SortType.STATUS);
        filter.setSortDirection(Sort.Direction.DESC);

        // act
        Page<OrderSummaryOutput> page = queryService.filter(filter);

        // assert
        Assertions.assertThat(page.getContent().getFirst().getStatus()).isEqualTo(OrderStatus.READY.toString());

    }
}
