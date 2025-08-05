package br.com.transformers.ems.algashop.ordering.domain.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderItem;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.PaymentMethod;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.BillingTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.ProductTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.ShippingTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.exception.InvalidShippingDateException;
import br.com.transformers.ems.algashop.ordering.domain.model.exception.OrderCannotBeChangedException;
import br.com.transformers.ems.algashop.ordering.domain.model.exception.OrderCannotBePlacedException;
import br.com.transformers.ems.algashop.ordering.domain.model.exception.UnavailableProductException;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Phone;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ProductName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderItemId;

public class OrderTest {

    @Test
    void testCreateDraft() {

        Order.draft(new CustomerId());

    }

    @Test
    void testGivinCorrectValuesWhenAddItemThenOrderItemAdded() {
        var order = OrderTestDataBuilder.anOrder().build();

        var product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(5);

        order.addItem(product, quantity);

        Assertions.assertThat(order.items()).isNotEmpty();

        var itemAdded = order.findOrderItemByProductId(product.id());

        Assertions.assertWith(itemAdded,
                (i) -> Assertions.assertThat(i.id()).isNotNull(),
                (i) -> Assertions.assertThat(i.productId()).isEqualTo(product.id()),
                (i) -> Assertions.assertThat(i.productName()).isEqualTo(product.name()),
                (i) -> Assertions.assertThat(i.price()).isEqualTo(product.value()),
                (i) -> Assertions.assertThat(i.quantity()).isEqualTo(quantity));
    }

    @Test
    void testUnmodifiedItems() {
        var order = OrderTestDataBuilder.anOrder().build();

        var product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(5);

        order.addItem(product, quantity);

        Assertions.assertThat(order.items()).isNotEmpty();

        var orderItems = order.items();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> orderItems.clear());
    }

    @Test
    void testGivenTwoCorrectItemsThenCorrectTotals() {
        var order = OrderTestDataBuilder.anOrder().build();

        var product = ProductTestDataBuilder.aProduct().build();
        order.addItem(product, new Quantity(5));

        product = ProductTestDataBuilder.aProduct()
                .name(new ProductName("pn2"))
                .value(new Money(BigDecimal.TEN))
            .build();

        order.addItem(product, new Quantity(2));

        Assertions.assertWith(order,
            (o) -> Assertions.assertThat(o.totalAmount()).isEqualTo(new Money("90")),
            (o) -> Assertions.assertThat(o.totalItems()).isEqualTo(new Quantity(14))
        );

    }

    @Test
    void testGivenDraftOrderWhenPlaceThenStatusPlaced() {

        var order = OrderTestDataBuilder.anOrder()
            .build();

        order.place();

        Assertions.assertWith(order,
            (o) -> Assertions.assertThat(o.isPlaced()).isTrue()
        );

    }

    @Test
    void testGivenPlaceOrderWhenCallPlaceThenException() {

        var order = OrderTestDataBuilder.anOrder()
            .build();

        order.place();

        Assertions.assertThatExceptionOfType(OrderCannotBePlacedException.class).isThrownBy(order::place);

    }

    @Test
    void testGivenPlaceOrderWhenPaidThenChangetoPaid() {

        var order = OrderTestDataBuilder.anOrder()
            .status(OrderStatus.PLACED)
            .build();

        order.markAsPaid();

        Assertions.assertWith(order,
            (o) -> Assertions.assertThat(o.isPaid()).isTrue(),
            (o) -> Assertions.assertThat(o.paidAt()).isNotNull()
        );

    }

    @Test
    void testGivenDraftOrderWhenChangePaymentMethodThenPaymentMethodChanged() {

        var order = Order.draft(new CustomerId());

        order.changePaymentMethod(PaymentMethod.GATEWAY_BALANCE);

        Assertions.assertWith(order,
            (o) -> Assertions.assertThat(o.paymentMethod()).isEqualTo(PaymentMethod.GATEWAY_BALANCE)
        );

    }

    @Test
    void testGivenDraftOrderWhenChangeBillingInfoThenBillingInfoChanged() {

        var order = Order.draft(new CustomerId());

        order.changeBilling(
            BillingTestDataBuilder.aBilling().build()
        );

        Assertions.assertWith(order,
            (o) -> Assertions.assertThat(o.billing().document()).isEqualTo(new Document("doct")),
            (o) -> Assertions.assertThat(o.billing().address().city()).isEqualTo("ssa"),
            (o) -> Assertions.assertThat(o.billing().phone()).isEqualTo(new Phone("phone"))
        );

    }

    @Test
    void testGivenDraftOrderWhenChangeShippingThenShippingInfoChanged() {

        var order = Order.draft(new CustomerId());

        order.changeShipping(ShippingTestDataBuilder.aShipping().build());

        Assertions.assertWith(order,
            (o) -> Assertions.assertThat(o.shipping().cost()).isEqualTo(new Money("10")),
            (o) -> Assertions.assertThat(o.shipping().expectedDate()).isEqualTo(LocalDate.now())
        );

    }

    @Test
    void testGivenDraftOrderInvalidExpectedDeliveryDateWhenChangeShippingThenException() {

        var order = Order.draft(new CustomerId());

        Assertions.assertThatExceptionOfType(InvalidShippingDateException.class).isThrownBy(() ->
            order.changeShipping(ShippingTestDataBuilder.aShipping()
                .expectedDate(LocalDate.now().minusMonths(1))
            .build())
        );

    }

    @Test
    void testGivenNewQuantityWhenChangeItemQuantityThenChanged() {
        
        var order = OrderTestDataBuilder.anOrder()
            .build();

        OrderItem orderItem = order.items().iterator().next();
        var orderItemId = orderItem.id();
        var newQuantity = new Quantity(13);

        order.changeQuantity(orderItemId, newQuantity);

        Assertions.assertWith(order, 
            (o) -> Assertions.assertThat(o.findOrderItem(orderItemId).quantity()).isEqualTo(newQuantity),
            (o) -> Assertions.assertThat(o.totalItems()).isEqualTo(new Quantity(order.totalItems().value() + newQuantity.value() - orderItem.quantity().value()))
        );
    }

    @Test
    void testGivenUnavailableProductWhenAddItemThenUnavailableProductException() {
        
        var order = Order.draft(new CustomerId());

        Assertions.assertThatExceptionOfType(UnavailableProductException.class)
            .isThrownBy(() ->order.addItem(ProductTestDataBuilder.aUnavailableProduct().build(), new Quantity(1)));
    }

    @Test
    void testGivenOrderInNonDraftStatusWhenChangeOrderThenOrderExceptionCannotBeChanged() {
        
        var order = OrderTestDataBuilder.anOrder().build();

        order.place();

        Assertions.assertThatExceptionOfType(OrderCannotBeChangedException.class)
            .isThrownBy(() ->order.changeBilling(BillingTestDataBuilder.aBilling().build()));
        Assertions.assertThatExceptionOfType(OrderCannotBeChangedException.class)
            .isThrownBy(() ->order.changePaymentMethod(PaymentMethod.CREDIT_CARD));
        Assertions.assertThatExceptionOfType(OrderCannotBeChangedException.class)
            .isThrownBy(() ->order.changeQuantity(new OrderItemId(), new Quantity(15)));
        Assertions.assertThatExceptionOfType(OrderCannotBeChangedException.class)
            .isThrownBy(() ->order.changeShipping(ShippingTestDataBuilder.aShipping().build()));
    }

}