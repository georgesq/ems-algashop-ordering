package br.com.transformers.ems.algashop.ordering.domain.model.repository;

import java.time.Year;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;
import br.com.transformers.ems.algashop.ordering.infrastructure.config.JpaConfig;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.provider.CustomersPersistenceProvider;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.provider.OrdersPersistenceProvider;

@DataJpaTest
@Import({
        OrdersPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class,
        CustomerPersistenceEntityAssembler.class,
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityDisassembler.class,
        JpaConfig.class
})
class OrdersIT {

    @Autowired
    private Orders orders;
    @Autowired
    private Customers customers;

    @BeforeEach
    public void setup() {
        if (!customers.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
            customers.add(
                    CustomerTestDataBuilder.existingCustomer().build());
        }
    }

    @Test
    @DisplayName("should save and retrieve an order")
    void saveAndFindById() {

        Order order = OrderTestDataBuilder.anOrder().build();

        orders.add(order);

        var savedOrder = orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(savedOrder).satisfies(
                s -> Assertions.assertThat(s.id()).isEqualTo(order.id()),
                s -> Assertions.assertThat(s.customerId()).isEqualTo(order.customerId()),
                s -> Assertions.assertThat(s.totalAmount()).isEqualTo(order.totalAmount()),
                s -> Assertions.assertThat(s.totalItems()).isEqualTo(order.totalItems()),
                s -> Assertions.assertThat(s.status()).isEqualTo(order.status()),
                s -> Assertions.assertThat(s.placedAt()).isEqualTo(order.placedAt()),
                s -> Assertions.assertThat(s.paidAt()).isEqualTo(order.paidAt()),
                s -> Assertions.assertThat(s.readAt()).isEqualTo(order.readAt()));

    }

    @Test
    @DisplayName("should update an existing order")
    void updateOrder() {

        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED)
                .build();

        orders.add(order);
        order = orders.ofId(order.id()).orElseThrow();

        order.markAsPaid();

        orders.add(order);
        order = orders.ofId(order.id()).orElseThrow();

        System.out.println(order);

        Assertions.assertThat(order).satisfies(
                s -> Assertions.assertThat(s.isPaid()).isTrue());

    }

    @Test
    @DisplayName("should concurrent update then error")
    void shouldConcurrentUpdateThenThrow() {

        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED)
                .build();

        orders.add(order);

        var savedOrder = orders.ofId(order.id()).orElseThrow();
        var savedOrder2 = orders.ofId(order.id()).orElseThrow();

        savedOrder.markAsPaid();
        orders.add(savedOrder);
        savedOrder2.cancel();

        Assertions.assertThatExceptionOfType(ObjectOptimisticLockingFailureException.class)
                .isThrownBy(() -> orders.add(savedOrder2));

        savedOrder = orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(savedOrder).satisfies(
                s -> Assertions.assertThat(s.isCanceled()).isFalse(),
                s -> Assertions.assertThat(s.isPaid()).isTrue());
    }

    @Test
    void shouldCountCorrect() {

        Assertions.assertThat(orders.count()).isZero();

        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();

        orders.add(order);

        Assertions.assertThat(orders).satisfies(
                o -> Assertions.assertThat(o.count()).isEqualTo(1L));

    }

    @Test
    void shouldExists() {

        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();
        orders.add(order);

        Assertions.assertThat(orders.exists(order.id())).isTrue();
        Assertions.assertThat(orders.exists(new OrderId())).isFalse();

    }

    @Test
    void givenYearThenListOrders() {

        Order order = OrderTestDataBuilder.anOrder().withItems(true).status(OrderStatus.PLACED).build();
        orders.add(order);
        Order aggregateRoot2 = OrderTestDataBuilder.anOrder().withItems(true).status(OrderStatus.PLACED).build();
        orders.add(aggregateRoot2);
        orders.add(OrderTestDataBuilder.anOrder().withItems(true).status(OrderStatus.CANCELED).build());
        orders.add(OrderTestDataBuilder.anOrder().withItems(true).status(OrderStatus.DRAFT).build());

        CustomerId customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;
        ;

        List<Order> ordersFinded = orders.placedByCustomerInYear(customerId, Year.now());

        Assertions.assertThat(ordersFinded).isNotEmpty();
        Assertions.assertThat(ordersFinded.size()).isEqualTo(2);

        ordersFinded = orders.placedByCustomerInYear(customerId, Year.now().minusYears(1));

        Assertions.assertThat(ordersFinded).isEmpty();

        ordersFinded = orders.placedByCustomerInYear(new CustomerId(), Year.now());

        Assertions.assertThat(ordersFinded).isEmpty();

    }

    @Test
    void givenCutomerAndYearThenCorrectTotalQuantity() {

        orders.add(OrderTestDataBuilder.anOrder().withItems(true).status(OrderStatus.PAID)
            .build());
        orders.add(OrderTestDataBuilder.anOrder().withItems(true).status(OrderStatus.PAID)
            .build());
        orders.add(OrderTestDataBuilder.anOrder().withItems(true).status(OrderStatus.CANCELED)
            .build());

        CustomerId customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;
        
        Assertions.assertThat(orders.salesQuantityByCustomerInYear(customerId, Year.now())).isEqualTo(2l);

    }

    @Test
    void givenCutomerThenCorrectTotalAmmount() {

        Order order1 = OrderTestDataBuilder.anOrder().withItems(true).status(OrderStatus.PAID)
            .build();
        orders.add(order1);
        Order order2 = OrderTestDataBuilder.anOrder().withItems(true).status(OrderStatus.PAID)
            .build();
        orders.add(order2);

        orders.add(OrderTestDataBuilder.anOrder().withItems(true).status(OrderStatus.CANCELED)
            .build());
        orders.add(OrderTestDataBuilder.anOrder().withItems(true).status(OrderStatus.PLACED)
            .build());

        CustomerId customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;
        
        Money expectedTA = order1.totalAmount().add(order2.totalAmount());

        Assertions.assertThat(orders.totalSoldByCustomer(customerId)).isEqualTo(expectedTA);

        Assertions.assertThat(orders.totalSoldByCustomer(new CustomerId())).isEqualTo(Money.ZERO);

    }
}