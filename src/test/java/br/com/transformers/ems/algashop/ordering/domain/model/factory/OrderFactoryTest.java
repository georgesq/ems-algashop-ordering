package br.com.transformers.ems.algashop.ordering.domain.model.factory;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.PaymentMethod;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.BillingTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.ProductTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.ShippingTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.factory.OrderFactory;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Billing;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Product;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Shipping;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;

class OrderFactoryTest {

    @Test
    void testFilledCreatesOrderWithAllFieldsSet() {
        CustomerId customerId = new CustomerId();
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        Billing billing = BillingTestDataBuilder.aBilling().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(2);

        var order = OrderFactory.filled(customerId, shipping, billing, paymentMethod, product, quantity);

        Assertions.assertWith(order,
            o -> Assertions.assertThat(o.customerId()).isEqualTo(customerId),
            o -> Assertions.assertThat(o.shipping()).isEqualTo(shipping),
            o -> Assertions.assertThat(o.billing()).isEqualTo(billing),
            o -> Assertions.assertThat(o.paymentMethod()).isEqualTo(paymentMethod),
            o -> Assertions.assertThat(o.items()).hasSize(1),
            o -> Assertions.assertThat(o.items().iterator().next().productId()).isEqualTo(product.id()),
            o -> Assertions.assertThat(o.items().iterator().next().quantity()).isEqualTo(quantity)
        );
    }

    @Test
    void testFilledThrowsExceptionWhenCustomerIdIsNull() {
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        Billing billing = BillingTestDataBuilder.aBilling().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(2);

        Assertions.assertThatNullPointerException()
            .isThrownBy(() -> OrderFactory.filled(null, shipping, billing, paymentMethod, product, quantity))
            .withMessageContaining("CustomerId must not be null");
    }

    @Test
    void testFilledThrowsExceptionWhenShippingIsNull() {
        CustomerId customerId = new CustomerId();
        Billing billing = BillingTestDataBuilder.aBilling().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(2);

        Assertions.assertThatNullPointerException()
            .isThrownBy(() -> OrderFactory.filled(customerId, null, billing, paymentMethod, product, quantity))
            .withMessageContaining("Shipping must not be null");
    }

    @Test
    void testFilledThrowsExceptionWhenBillingIsNull() {
        CustomerId customerId = new CustomerId();
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(2);

        Assertions.assertThatNullPointerException()
            .isThrownBy(() -> OrderFactory.filled(customerId, shipping, null, paymentMethod, product, quantity))
            .withMessageContaining("Billing must not be null");
    }

    @Test
    void testFilledThrowsExceptionWhenPaymentMethodIsNull() {
        CustomerId customerId = new CustomerId();
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        Billing billing = BillingTestDataBuilder.aBilling().build();
        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(2);

        Assertions.assertThatNullPointerException()
            .isThrownBy(() -> OrderFactory.filled(customerId, shipping, billing, null, product, quantity))
            .withMessageContaining("PaymentMethod must not be null");
    }

    @Test
    void testFilledThrowsExceptionWhenProductIsNull() {
        CustomerId customerId = new CustomerId();
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        Billing billing = BillingTestDataBuilder.aBilling().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
        Quantity quantity = new Quantity(2);

        Assertions.assertThatNullPointerException()
            .isThrownBy(() -> OrderFactory.filled(customerId, shipping, billing, paymentMethod, null, quantity))
            .withMessageContaining("Product must not be null");
    }

    @Test
    void testFilledThrowsExceptionWhenQuantityIsNull() {
        CustomerId customerId = new CustomerId();
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        Billing billing = BillingTestDataBuilder.aBilling().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
        Product product = ProductTestDataBuilder.aProduct().build();

        Assertions.assertThatNullPointerException()
            .isThrownBy(() -> OrderFactory.filled(customerId, shipping, billing, paymentMethod, product, null))
            .withMessageContaining("Quantity must not be null");
    }
}