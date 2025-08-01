package br.com.transformers.ems.algashop.ordering.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.entity.databuilder.OrderTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.entity.databuilder.ProductTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.entity.databuilder.ShippingTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.exception.InvalidShippingDateException;
import br.com.transformers.ems.algashop.ordering.domain.exception.OrderExceptionCannotBeChanged;
import br.com.transformers.ems.algashop.ordering.domain.exception.UnavailableProductException;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Address;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Billing;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.FullName;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Phone;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.ProductName;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.CustomerId;

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
            .status(OrderStatus.PLACED)
            .build();

        Assertions.assertThatExceptionOfType(OrderExceptionCannotBeChanged.class).isThrownBy(order::place);

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
            Billing.builder()
                .document(new Document("doct"))
                .fullName(new FullName("fn", "ln"))
                .address(Address.builder()
                    .city("Salvador")
                    .build()
                )
                .phone(new Phone("phone"))
            .build()
        );

        Assertions.assertWith(order,
            (o) -> Assertions.assertThat(o.billing().document()).isEqualTo(new Document("doct")),
            (o) -> Assertions.assertThat(o.billing().address().city()).isEqualTo("Salvador"),
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

        var orderItemId = order.items().iterator().next().id();
        var newQuantity = new Quantity(13);

        order.changeQuantity(orderItemId, newQuantity);

        Assertions.assertWith(order, 
            (o) -> Assertions.assertThat(o.items().iterator().next().quantity()).isEqualTo(newQuantity),
            (o) -> Assertions.assertThat(o.totalItems()).isEqualTo(new Quantity(18))
        );
    }

    @Test
    void testGivenUnavailableProductWhenAddItemThenUnavailableProductException() {
        
        var order = Order.draft(new CustomerId());

        Assertions.assertThatExceptionOfType(UnavailableProductException.class)
            .isThrownBy(() ->order.addItem(ProductTestDataBuilder.aUnavailableProduct().build(), new Quantity(1)));
    }
}
