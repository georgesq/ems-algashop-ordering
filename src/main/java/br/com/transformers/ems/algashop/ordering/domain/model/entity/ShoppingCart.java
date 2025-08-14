package br.com.transformers.ems.algashop.ordering.domain.model.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Product;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;

public class ShoppingCart implements AggregateRoot<ShoppingCartId> {

    private ShoppingCartId id;
    private CustomerId customerId;
    private Money totalAmount;
    private Quantity totalItems;
    private OffsetDateTime createdAt;
    private Set<ShoppingCartItem> items;

    private void setId(ShoppingCartId id) {
        this.id = id;
    }

    private void setCustomerId(CustomerId customerId) {
        this.customerId = customerId;
    }

    private void setTotalAmount(Money totalAmount) {
        this.totalAmount = totalAmount;
    }

    private void setTotalItems(Quantity totalItems) {
        this.totalItems = totalItems;
    }

    private void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private void setItems(Set<ShoppingCartItem> items) {
        this.items = items;
    }

    private void recalculateTotals() {

        BigDecimal totalAmmount = this.items.stream()
            .map(i -> i.totalAmmount().value())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        Long totalItemsQuantity = this.items.stream()
            .map(i -> i.quantity().value())
            .reduce(0L, Long::sum);            

        this.setTotalAmount(new Money(totalAmmount));
        this.setTotalItems(new Quantity(totalItemsQuantity));

    }

    public static ShoppingCart startShopping(CustomerId customerId) {

        ShoppingCart shoppingCart = new ShoppingCart();
 
        shoppingCart.setId(new ShoppingCartId());
        shoppingCart.setCustomerId(customerId);
        shoppingCart.setTotalAmount(Money.ZERO);
        shoppingCart.setTotalItems(Quantity.ZERO);
        shoppingCart.setCreatedAt(OffsetDateTime.now());
        shoppingCart.setItems(new HashSet<>());

        return shoppingCart;
        
    }

    public void empty() {

        this.items.clear();
        this.recalculateTotals();

    }

    public void removeItem(ShoppingCartItemId id) {

        if (!isEmpty()) {
            this.items.remove(this.findItem(id));
            this.recalculateTotals();
        }
        
    }

    public void addItem(Product product, Quantity quantity) {
        
        ShoppingCartItem item = ShoppingCartItem.brandNew(this.id(), product, quantity);

        this.items.add(item);
     
        this.recalculateTotals();

    }

    public void refreshItem(Product product) {

        var item = this.findItem(product);

        item.refresh(product);

        this.recalculateTotals();

    }

    public ShoppingCartItem findItem(ShoppingCartItemId id) {

        return this.items.stream()
                .filter(item -> item.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

    }

    public ShoppingCartItem findItem(Product product) {

        return this.items.stream()
                .filter(item -> item.productId().equals(product.id()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

    }

    public void changeItem(ShoppingCartItemId id, Quantity quantity, Boolean available) {

        ShoppingCartItem item = this.findItem(id);

        item.changeQuantity(quantity);
        item.changeAvaliable(available);

        this.recalculateTotals();

    }

    public boolean containsUnavailableItems() {

        return this.items.stream()
                .anyMatch(item -> !item.available());

    }

    public boolean isEmpty() {

        return this.items.isEmpty();
        
    }

    public ShoppingCartId id() {
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

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public Set<ShoppingCartItem> items() {
        return Collections.unmodifiableSet(items);
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
        ShoppingCart other = (ShoppingCart) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


}
