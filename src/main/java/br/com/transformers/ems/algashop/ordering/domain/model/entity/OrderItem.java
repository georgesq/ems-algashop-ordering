package br.com.transformers.ems.algashop.ordering.domain.model.entity;

import java.util.Objects;

import br.com.transformers.ems.algashop.ordering.domain.model.validator.NotNullNonEmptyValidator;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Product;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ProductName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderItemId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ProductId;
import lombok.Builder;

public class OrderItem {

    private static final NotNullNonEmptyValidator NNEV = NotNullNonEmptyValidator.getInstance();

    private OrderItemId id;
    private OrderId orderId;

    private ProductId productId;
    private ProductName productName;

    private Money price;
    private Quantity quantity;

    private Money totalAmmount;

    @Builder(builderClassName = "ExistingOrderItemBuilder", builderMethodName = "existing")
    public OrderItem(OrderItemId id, OrderId orderId, ProductId productId, ProductName productName, Money price,
            Quantity quantity, Money totalAmmount) {
        this.setId(id);
        this.setOrderId(orderId);
        this.setProductId(productId);
        this.setProductName(productName);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setTotalAmmount(totalAmmount);
    }

    @Builder(builderClassName = "BrandNewOrderItemBuilder", builderMethodName = "brandNew")
    public static OrderItem brandNew(OrderId orderId, Product product, Quantity quantity) {

        Objects.requireNonNull(orderId);
        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        OrderItem orderItem = new OrderItem(
                new OrderItemId(),
                orderId,
                product.id(),
                product.name(),
                product.value(),
                quantity,
                Money.ZERO);

        orderItem.recalculateTotals();

        return orderItem;

    }

    public OrderItemId id() {
        return id;
    }

    public OrderId orderId() {
        return orderId;
    }

    public ProductId productId() {
        return productId;
    }

    public ProductName productName() {
        return productName;
    }

    public Money price() {
        return price;
    }

    public Quantity quantity() {
        return quantity;
    }

    public Money totalAmmount() {
        return totalAmmount;
    }

    private void setId(OrderItemId id) {

        if (!NNEV.isValid(id, null)) {
            throw new IllegalArgumentException();
        }

        this.id = id;
    }

    private void setOrderId(OrderId orderId) {

        if (!NNEV.isValid(orderId, null)) {
            throw new IllegalArgumentException();
        }

        this.orderId = orderId;
    }

    private void setProductId(ProductId productId) {

        if (!NNEV.isValid(productId, null)) {
            throw new IllegalArgumentException();
        }

        this.productId = productId;
    }

    private void setProductName(ProductName productName) {

        if (!NNEV.isValid(productName, null)) {
            throw new IllegalArgumentException();
        }

        this.productName = productName;
    }

    private void setPrice(Money price) {

        if (!NNEV.isValid(price, null)) {
            throw new IllegalArgumentException();
        }

        this.price = price;
    }

    private void setQuantity(Quantity quantity) {

        if (!NNEV.isValid(quantity, null)) {
            throw new IllegalArgumentException();
        }

        this.quantity = quantity;
    }

    private void setTotalAmmount(Money totalAmmount) {

        if (!NNEV.isValid(totalAmmount, null)) {
            throw new IllegalArgumentException();
        }

        this.totalAmmount = totalAmmount;

    }

    public void recalculateTotals() {

        this.setTotalAmmount(this.price().multiply(this.quantity()));

    }

    void changeQuantity(Quantity value) {
        this.setQuantity(value);
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
        OrderItem other = (OrderItem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
