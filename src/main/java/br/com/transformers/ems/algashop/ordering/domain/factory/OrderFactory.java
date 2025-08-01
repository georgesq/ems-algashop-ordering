package br.com.transformers.ems.algashop.ordering.domain.factory;

import java.util.Objects;

import br.com.transformers.ems.algashop.ordering.domain.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.entity.PaymentMethod;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Billing;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Product;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Shipping;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.CustomerId;

public class OrderFactory {
    
    private OrderFactory() {

    }

    public static Order filled(
        CustomerId customerId,
        Shipping shipping,
        Billing billing,
        PaymentMethod paymentMethod,
        Product product,
        Quantity quantity
    ) {

        Objects.requireNonNull(customerId, "CustomerId must not be null");
        Objects.requireNonNull(shipping, "Shipping must not be null");
        Objects.requireNonNull(billing, "Billing must not be null");
        Objects.requireNonNull(paymentMethod, "PaymentMethod must not be null");
        Objects.requireNonNull(product, "Product must not be null");
        Objects.requireNonNull(quantity, "Quantity must not be null");

        var order = Order.draft(customerId);

        order.changeShipping(shipping);
        order.changeBilling(billing);
        order.changePaymentMethod(paymentMethod);
        order.addItem(product, quantity);

        return order;

    }
}
