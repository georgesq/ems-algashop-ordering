package br.com.transformers.ems.algashop.ordering.domain.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

import br.com.transformers.ems.algashop.ordering.domain.model.exception.ShoppingCartItemIncompatibleProductException;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Product;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ProductName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ProductId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;
import lombok.Builder;

public class ShoppingCartItem {

    private ShoppingCartItemId id;

    private ShoppingCartId shoppingCartId;

    private ProductId productId;
    private ProductName productName;

    private Money price;
    private Quantity quantity;

    private Money totalAmmount;

    private Boolean available;

    @Builder(builderClassName = "ExistingShoppingCartBuilder", builderMethodName = "existing")
    public ShoppingCartItem(ShoppingCartItemId id, ShoppingCartId shoppingCartId,
            ProductId productId, ProductName productName, Money price, Quantity quantity, Money totalAmmount, Boolean available) {

        this.setId(id);
        this.setShoppingCartId(shoppingCartId);
        this.setProductId(productId);
        this.setProductName(productName);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setAvailable(true);
        this.setTotalAmmount(totalAmmount);
        this.setAvailable(available);

    }

    @Builder(builderClassName = "ShoppingCartItemBuilder", builderMethodName = "brandNew")
    public static ShoppingCartItem brandNew(ShoppingCartId shoppingCartId, Product product, Quantity quantity) {

        Objects.requireNonNull(shoppingCartId);
        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        return new ShoppingCartItem(
            new ShoppingCartItemId(),
            shoppingCartId,
            product.id(),
            product.name(),
            product.value(),
            quantity,
            recalculate(product.value(), quantity),
            product.inStock()
        );

    }

    protected void refresh(Product product) {

        if (this.productId != product.id()) {
            throw new ShoppingCartItemIncompatibleProductException(this.shoppingCartId, this.id, product.id());
        }

        this.setProductName(product.name());
        this.setPrice(product.value());
        this.setAvailable(product.inStock());

        recalculate(this.price, this.quantity());
    }

    protected void changeQuantity(Quantity quantity) {
        if (quantity == null || quantity.value() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        
        this.quantity = quantity;

        this.setTotalAmmount(recalculate(this.price, this.quantity()));

    }

    public ShoppingCartItemId id() {
        return id;
    }

    public ShoppingCartId shoppingCartId() {
        return shoppingCartId;
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

    public boolean available() {
        return available;
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
        ShoppingCartItem other = (ShoppingCartItem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    private ShoppingCartItem setId(ShoppingCartItemId id) {
        this.id = id;
        return this;
    }

    private ShoppingCartItem setShoppingCartId(ShoppingCartId shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    private ShoppingCartItem setProductId(ProductId productId) {
        this.productId = productId;
        return this;
    }

    private ShoppingCartItem setProductName(ProductName productName) {
        this.productName = productName;
        return this;
    }

    private ShoppingCartItem setPrice(Money price) {
        this.price = price;
        return this;
    }

    private ShoppingCartItem setQuantity(Quantity quantity) {
        this.quantity = quantity;
        return this;
    }

    private ShoppingCartItem setTotalAmmount(Money totalAmmount) {
        this.totalAmmount = totalAmmount;
        return this;
    }

    private ShoppingCartItem setAvailable(Boolean available) {
        this.available = available;
        return this;
    }

    private static Money recalculate(Money productValue, Quantity quantity) {
        return new Money(new BigDecimal(quantity.value()).multiply(productValue.value()));
    }

    public void changeAvaliable(Boolean available) {
        this.setAvailable(available);
    }

}
