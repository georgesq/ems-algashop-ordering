package br.com.transformers.ems.algashop.ordering.domain.model.factory;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.PaymentMethod;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.BillingTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.ProductTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.ShippingTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Billing;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Product;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Shipping;

class OrderFactoryTest {

    @Test
    void testFilledCreatesOrderWithAllFieldsSet() {
        Customer customer = CustomerTestDataBuilder.aCustomerBuilder().build();
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        Billing billing = BillingTestDataBuilder.aBilling().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(2L);

        var order = OrderFactory.filled(customer, shipping, billing, paymentMethod, product, quantity);

        Assertions.assertWith(order,
            o -> Assertions.assertThat(o.customer()).isEqualTo(customer),
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
        Quantity quantity = new Quantity(2L);

        Assertions.assertThatNullPointerException()
            .isThrownBy(() -> OrderFactory.filled(null, shipping, billing, paymentMethod, product, quantity))
            .withMessageContaining("CustomerId must not be null");
    }

    @Test
    void testFilledThrowsExceptionWhenShippingIsNull() {
        Customer customer = CustomerTestDataBuilder.aCustomerBuilder().build();
        Billing billing = BillingTestDataBuilder.aBilling().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(2L);

        Assertions.assertThatNullPointerException()
            .isThrownBy(() -> OrderFactory.filled(customer, null, billing, paymentMethod, product, quantity))
            .withMessageContaining("Shipping must not be null");
    }

    @Test
    void testFilledThrowsExceptionWhenBillingIsNull() {
        Customer customer = CustomerTestDataBuilder.aCustomerBuilder().build();
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(2L);

        Assertions.assertThatNullPointerException()
            .isThrownBy(() -> OrderFactory.filled(customer, shipping, null, paymentMethod, product, quantity))
            .withMessageContaining("Billing must not be null");
    }

    @Test
    void testFilledThrowsExceptionWhenPaymentMethodIsNull() {
        Customer customer = CustomerTestDataBuilder.aCustomerBuilder().build();
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        Billing billing = BillingTestDataBuilder.aBilling().build();
        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(2L);

        Assertions.assertThatNullPointerException()
            .isThrownBy(() -> OrderFactory.filled(customer, shipping, billing, null, product, quantity))
            .withMessageContaining("PaymentMethod must not be null");
    }

    @Test
    void testFilledThrowsExceptionWhenProductIsNull() {
        Customer customer = CustomerTestDataBuilder.aCustomerBuilder().build();
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        Billing billing = BillingTestDataBuilder.aBilling().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
        Quantity quantity = new Quantity(2L);

        Assertions.assertThatNullPointerException()
            .isThrownBy(() -> OrderFactory.filled(customer, shipping, billing, paymentMethod, null, quantity))
            .withMessageContaining("Product must not be null");
    }

    @Test
    void testFilledThrowsExceptionWhenQuantityIsNull() {
        Customer customer = CustomerTestDataBuilder.aCustomerBuilder().build();
        Shipping shipping = ShippingTestDataBuilder.aShipping().build();
        Billing billing = BillingTestDataBuilder.aBilling().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
        Product product = ProductTestDataBuilder.aProduct().build();

        Assertions.assertThatNullPointerException()
            .isThrownBy(() -> OrderFactory.filled(customer, shipping, billing, paymentMethod, product, null))
            .withMessageContaining("Quantity must not be null");
    }
}