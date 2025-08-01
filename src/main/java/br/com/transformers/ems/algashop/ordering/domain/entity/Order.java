package br.com.transformers.ems.algashop.ordering.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import br.com.transformers.ems.algashop.ordering.domain.exception.OrderCannotBePlacedException;
import br.com.transformers.ems.algashop.ordering.domain.exception.OrderCannotChangeItemException;
import br.com.transformers.ems.algashop.ordering.domain.exception.OrderExceptionCannotBeChanged;
import br.com.transformers.ems.algashop.ordering.domain.exception.OrderItemNoFoundException;
import br.com.transformers.ems.algashop.ordering.domain.exception.PaidException;
import br.com.transformers.ems.algashop.ordering.domain.exception.ReadyException;
import br.com.transformers.ems.algashop.ordering.domain.exception.UnavailableProductException;
import br.com.transformers.ems.algashop.ordering.domain.validator.NotNullNonEmptyValidator;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Billing;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Product;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Shipping;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.OrderId;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.OrderItemId;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.ProductId;
import lombok.Builder;

public class Order {

    private static final NotNullNonEmptyValidator NNEV = NotNullNonEmptyValidator.getInstance();

    private OrderId id;
    private CustomerId customerId;

    private Money totalAmount;
    private Quantity totalItems;
    
    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime readAt;
    
    private Billing billing;
    private Shipping shipping;
    
    private OrderStatus status;
    private PaymentMethod paymentMethod;
    
    private Set<OrderItem> items;

    @Builder(builderClassName = "ExistingOrderBuilder", builderMethodName = "existing")    
    public Order(
        OrderId id, 
        CustomerId customerId,
        Money totalAmount, 
        Quantity totalItems, 
        OffsetDateTime placedAt,
        OffsetDateTime paidAt, 
        OffsetDateTime canceledAt, 
        OffsetDateTime readAt, 
        Billing billing,
        Shipping shipping, 
        OrderStatus status, 
        PaymentMethod paymentMethod,
        Set<OrderItem> items) {

        this.setId(id);
        this.setCustomerId(customerId);
        this.setTotalAmount(totalAmount);
        this.setTotalItems(totalItems);
        this.setPlacedAt(placedAt);
        this.setPaidAt(paidAt);
        this.setCanceledAt(canceledAt);
        this.setReadAt(readAt);
        this.setBilling(billing);
        this.setShipping(shipping);
        this.setStatus(status);
        this.setPaymentMethod(paymentMethod);
        this.setShipping(shipping);
        this.setItems(items);

    }

    @Builder(builderClassName = "NewSimpleOrderBuild", builderMethodName = "draft")
    public static Order draft(CustomerId customerId) {

        return new Order(
            new OrderId(), 
            customerId, 
            Money.ZERO, 
            Quantity.ZERO, 
            null,
            null, 
            null, 
            null, 
            null, 
            null,
            OrderStatus.DRAFT, 
            null, 
            new HashSet<OrderItem>()
        );
        
    }

    private void setId(OrderId id) {

        if (!NNEV.isValid(id, null)) {
            throw new IllegalArgumentException();
        }

        this.id = id;
    }

    private void setTotalAmount(Money totalAmount) {
        this.totalAmount = totalAmount;
    }

    private void setTotalItems(Quantity totalItems) {
        this.totalItems = totalItems;
    }

    private void setPlacedAt(OffsetDateTime placedAt) {
        this.placedAt = placedAt;
    }

    private void setPaidAt(OffsetDateTime paidAt) {
        this.paidAt = paidAt;
    }

    private void setCanceledAt(OffsetDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    private void setReadAt(OffsetDateTime readAt) {
        this.readAt = readAt;
    }

    private void setCustomerId(CustomerId customerId) {

        if (!NNEV.isValid(customerId, null)) {
            throw new IllegalArgumentException();
        }

        this.customerId = customerId;
    }

    private void setBilling(Billing billingInfo) {
        this.billing = billingInfo;
    }

    private void setShipping(Shipping shippingInfo) {
        this.shipping = shippingInfo;
    }

    private void setStatus(OrderStatus status) {

        if (!NNEV.isValid(status, null)) {
            throw new IllegalArgumentException();
        }

        this.status = status;
    }

    private void setPaymentMethod(PaymentMethod paymentMethod) {

        this.paymentMethod = paymentMethod;

    }

    private void setItems(Set<OrderItem> items) {

        if (!NNEV.isValid(items, null)) {
            throw new IllegalArgumentException();
        }

        this.items = items;
    }

    private void changeStatus(OrderStatus newStatus) {

        Objects.requireNonNull(newStatus);

        this.setStatus(newStatus);
    }

    private void verifyIfChangeable() {
        if (!this.isDraft()) {
            throw new OrderExceptionCannotBeChanged(this.id(), this.status(), OrderStatus.DRAFT);
        }
    }

    private void verifyIfCanChangeToPlace() {
        if (this.isPlaced()) {
            throw OrderCannotBePlacedException.noShipping(this.id());
        }
        if (Objects.isNull(this.shipping())) {
            throw OrderCannotBePlacedException.noShipping(this.id());
        }
        if (Objects.isNull(this.billing())) {
            throw OrderCannotBePlacedException.noBilling(this.id());
        }
        if (Objects.isNull(this.paymentMethod())) {
            throw OrderCannotBePlacedException.noPaymentMethod(this.id());
        }
        if (Objects.isNull(this.items()) || this.items().isEmpty()) {
            throw OrderCannotBePlacedException.noItems(this.id());
        }
    }

    public OrderItem findOrderItem(OrderItemId orderItemId) {
        return this.items().stream().filter(i -> i.id() == orderItemId).findFirst().orElseThrow(() -> new OrderItemNoFoundException(this.id()));
    }

    private void verifyRequireds(OrderItemId orderItemId, Quantity quantity) {
        if (Objects.isNull(orderItemId)) {
            OrderCannotChangeItemException.noOrderItemId(this.id);
        }
        if (Objects.isNull(quantity)) {
            OrderCannotChangeItemException.noQuantity(this.id);
        }
    }

    private void verifyProduct(Product product) {
        Objects.requireNonNull(product);

        if (product.checkOutOfStock()) {
            throw new UnavailableProductException(product.id());
        }
    }

    public void markAsPaid() {
        if (isPaid()) {
            throw new PaidException();
        }

        this.setPaidAt(OffsetDateTime.now());
        this.changeStatus(OrderStatus.PAID);
    }

    public void markAsReady() {
        if (this.isReady()) {
            throw new ReadyException();
        }

        this.setPaidAt(OffsetDateTime.now());
    }

    public boolean isCanceled() {
        return this.status().equals(OrderStatus.CANCELED);
    }

    public boolean isPaid() {
        return this.status().equals(OrderStatus.PAID);
    }

    public boolean isReady() {
        return this.status().equals(OrderStatus.READY);
    }

    public boolean isDraft() {
        return this.status().equals(OrderStatus.DRAFT);
    }

    public boolean isPlaced() {
        return this.status().equals(OrderStatus.PLACED);
    }

    public void addItem(Product product, Quantity quantity) {

        verifyProduct(product);
        Objects.requireNonNull(quantity);

        this.items.add(OrderItem.brandNew(
            this.id(), 
            product, 
            quantity)
        );

        if (Objects.isNull(this.shipping())) {
            this.setShipping(Shipping.builder()
                    .cost(Money.ZERO)
                    .expectedDate(LocalDate.now())
                .build());
        }
        
        this.recalculateTotals();
    }

    public void place() {

        verifyIfCanChangeToPlace();

        this.setPlacedAt(OffsetDateTime.now());
        this.changeStatus(OrderStatus.PLACED);

    }

    public void recalculateTotals() {

        BigDecimal totalItemsAmmount = this.items.stream()
            .map(i -> i.totalAmmount().value())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        Integer totalItemsQuantity = this.items.stream()
            .map(i -> i.quantity().value())
            .reduce(0, Integer::sum);            

        BigDecimal shippingCost  = this.shipping().cost().value();

        var totalAmount = totalItemsAmmount.add(shippingCost);

        this.setTotalAmount(new Money(totalAmount));
        this.setTotalItems(new Quantity(totalItemsQuantity));

    }

    public void changeBilling(Billing billing) {

        Objects.requireNonNull(billing);
        this.verifyIfChangeable();

        this.setBilling(billing);

    }

    public void changeShipping(Shipping shipping) {

        Objects.requireNonNull(shipping);
        this.verifyIfChangeable();

        this.setShipping(shipping);

    }

    public void changeQuantity(OrderItemId orderItemId, Quantity quantity) {

        this.verifyIfChangeable();
        this.verifyRequireds(orderItemId, quantity);

        var orderItem = this.findOrderItem(orderItemId);

        orderItem.changeQuantity(quantity);

        this.recalculateTotals();

    }

    public void removeItem(OrderItemId orderItemId) {

    }

    public void changePaymentMethod(PaymentMethod paymentMethod) {

        Objects.requireNonNull(paymentMethod);
        this.verifyIfChangeable();
        
        this.setPaymentMethod(paymentMethod);

    }

    public OrderId id() {
        return id;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    public Quantity totalItems() {
        return totalItems;
    }

    public OffsetDateTime placedAt() {
        return placedAt;
    }

    public OffsetDateTime paidAt() {
        return paidAt;
    }

    public OffsetDateTime canceledAt() {
        return canceledAt;
    }

    public OffsetDateTime readAt() {
        return readAt;
    }

    public Billing billing() {
        return billing;
    }

    public Shipping shipping() {
        return shipping;
    }

    public OrderStatus status() {
        return status;
    }

    public PaymentMethod paymentMethod() {
        return paymentMethod;
    }

    public Set<OrderItem> items() {
        return Collections.unmodifiableSet(this.items);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public void markAsCancel() {

        this.setCanceledAt(OffsetDateTime.now());
        this.changeStatus(OrderStatus.CANCELED);

    }

    public OrderItem findOrderItemByProductId(ProductId productId) {
        return this.items().stream().filter(i -> i.productId() == productId).findFirst().orElseThrow(() -> new OrderItemNoFoundException(this.id()));
    }

}
