package com.algaworks.algashop.ordering.application.order.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.application.utility.PageFilter;
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

        // arranje
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

        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).withItems(false).customerId(customer.id()).build());
        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).customerId(customer.id()).build());
        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).customerId(customer.id()).build());
        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.READY).customerId(customer.id()).build());
        this.orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).customerId(customer.id()).build());

        // act
        Page<OrderSummaryOutput> output = this.queryService.filter(new PageFilter(2, 0));;

        // assert
        Assertions.assertThat(output).isNotNull();
        Assertions.assertThat(output.getSize()).isEqualTo(2l);

    }
}
