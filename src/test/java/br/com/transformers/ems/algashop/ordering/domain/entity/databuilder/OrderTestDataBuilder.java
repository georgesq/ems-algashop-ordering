package br.com.transformers.ems.algashop.ordering.domain.entity.databuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.transformers.ems.algashop.ordering.domain.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.entity.PaymentMethod;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Address;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Billing;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.FullName;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Phone;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Product;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Shipping;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.ZipCode;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.CustomerId;

public class OrderTestDataBuilder {

    CustomerId customerId = new CustomerId();

    PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;

    Money shippingCost = new Money("150");
    LocalDate expectedDeliveryDate = LocalDate.now().plusWeeks(1);

    Shipping shipping = aShipping();
    Billing billing = aBilling();

    boolean withItems = true;

    OrderStatus status = OrderStatus.DRAFT;

    private OrderTestDataBuilder() {

    }

    public static Billing aBilling() {
        return Billing.builder()
                .document(new Document("doct"))
                .fullName(new FullName("fn", "ln"))
                .address(anAddress())
                .phone(new Phone("phone"))
                .email(new Email("a@aaa.com"))
                .build();

    }

    public static Address anAddress() {
        return Address.builder()
            .city("Salvador")
            .number(178)
            .street("r. bea")        
            .complement("ap. 1001")
            .neighborhood("boca do rio")
            .state("BA")
            .zipCode(new ZipCode("41710790"))
        .build();
    }

    public static Shipping aShipping() {
        return Shipping.builder()
            .cost(Money.ZERO)
            .expectedDate(LocalDate.now())
            .recipient(RecipientTestDataBuilder.aRecipient().build())
            .build();
    }

    public static OrderTestDataBuilder anOrder() {
        return new OrderTestDataBuilder();
    }

    public static OrderTestDataBuilder anPaidOrder() {
        return anOrder()
            .status(OrderStatus.PAID)
            ;
    }

    public Order build() {
        
        Order order = Order.draft(this.customerId);

        order.changeShipping(this.shipping);
        order.changeBilling(this.billing);
        order.changePaymentMethod(this.paymentMethod);

        if (withItems) {
            order.addItem(ProductTestDataBuilder.aProduct()
                .value(new Money(BigDecimal.TEN))
                .build(), 
                new Quantity(5));
            order.addItem(ProductTestDataBuilder.aProduct()
                .value(new Money(BigDecimal.TEN))
                .build(), 
                new Quantity(2));
        }

        switch (this.status) {
            case DRAFT -> {

            }
        
            case READY -> {
                
            }

            case PLACED -> {

                order.place();

            }

            case PAID -> {
                
                order.place();
                order.markAsPaid();

            }
            case CANCELED -> {

                order.markAsCancel();

            }

        }

        return order;

    }

    public OrderTestDataBuilder customerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderTestDataBuilder paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderTestDataBuilder shippingCost(Money shippingCost) {
        this.shippingCost = shippingCost;
        return this;
    }

    public OrderTestDataBuilder expectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;

        return this;
    }

    public OrderTestDataBuilder shippingInfo(Shipping shippingInfo) {
        this.shipping = shippingInfo;
        return this;
    }

    public OrderTestDataBuilder billingInfo(Billing billingInfo) {
        this.billing = billingInfo;
        return this;
    }

    public OrderTestDataBuilder withItems(boolean withItems) {
        this.withItems = withItems;
        return this;
    }

    public OrderTestDataBuilder status(OrderStatus status) {
        this.status = status;
        return this;
    }

}
