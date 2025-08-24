package br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.PaymentMethod;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Address;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Billing;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.FullName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Phone;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Shipping;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ZipCode;

public class OrderTestDataBuilder {

    Customer customer = CustomerTestDataBuilder.aCustomerBuilder().build();

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
            .address(AddressTestDataBuilder.anAddress().build())
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

    public static OrderTestDataBuilder anCanCancelOrder() {
        return anOrder()
            .status(OrderStatus.READY)
            ;
    }

    public Order build() {
        
        Order order = Order.draft(this.customer);

        order.changeShipping(this.shipping);
        order.changeBilling(this.billing);
        order.changePaymentMethod(this.paymentMethod);

        if (withItems) {
            order.addItem(ProductTestDataBuilder.aProduct()
                .value(new Money(BigDecimal.TEN))
                .build(), 
                new Quantity(5L));
            order.addItem(ProductTestDataBuilder.aProduct()
                .value(new Money(BigDecimal.TEN))
                .build(), 
                new Quantity(2L));
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

    public OrderTestDataBuilder customer(Customer customer) {

        this.customer = customer;

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
