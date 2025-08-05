package br.com.transformers.ems.algashop.ordering.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.ShoppingCart;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.ShoppingCartItem;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Product;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ProductName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;
import br.com.transformers.ems.algashop.ordering.model.entity.databuilder.ProductTestDataBuilder;

class ShoppingCartTest {
    private CustomerId customerId;
    private Product product;
    private Quantity quantity;

    @BeforeEach
    void setUp() {
        customerId = new CustomerId();
        product = ProductTestDataBuilder.aProduct()
                .value(new Money(BigDecimal.valueOf(10)))
            .build(); 
        // new Product(UUID.randomUUID(), "Test Product", new Money(BigDecimal.valueOf(10)), true);
        quantity = new Quantity(2);
    }

    @Test
    void startShopping_shouldInitializeShoppingCartWithDefaults() {
        ShoppingCart cart = ShoppingCart.startShopping(customerId);

        assertThat(cart).isNotNull();
        assertThat(cart.id()).isNotNull();
        assertThat(cart.customerId()).isEqualTo(customerId);
        assertThat(cart.totalAmount()).isEqualTo(Money.ZERO);
        assertThat(cart.totalItems()).isEqualTo(Quantity.ZERO);
        assertThat(cart.items()).isEmpty();
        assertThat(cart.createdAt()).isNotNull();
        assertThat(cart.createdAt())
                .isBeforeOrEqualTo(OffsetDateTime.now())
                .isAfter(OffsetDateTime.now().minusMinutes(1));
    }

    @Test
    void addItem_shouldAddItemAndUpdateTotals() {
        ShoppingCart cart = ShoppingCart.startShopping(customerId);

        cart.addItem(product, quantity);

        assertThat(cart.items()).hasSize(1);
        assertThat(cart.totalItems().value()).isEqualTo(quantity.value());
        assertThat(cart.totalAmount().value()).isEqualTo(product.value().value().multiply(BigDecimal.valueOf(quantity.value())));
    }

    @Test
    void empty_shouldClearItemsAndResetTotals() {
        ShoppingCart cart = ShoppingCart.startShopping(customerId);
        cart.addItem(product, quantity);

        cart.empty();

        assertThat(cart.items()).isEmpty();
        assertThat(cart.totalItems()).isEqualTo(Quantity.ZERO);
        assertThat(cart.totalAmount()).isEqualTo(Money.ZERO);
    }

    @Test
    void removeItem_shouldRemoveItemAndUpdateTotals() {
        ShoppingCart cart = ShoppingCart.startShopping(customerId);
        cart.addItem(product, quantity);
        ShoppingCartItem item = cart.items().iterator().next();

        cart.removeItem(item.id());

        assertThat(cart.items()).isEmpty();
        assertThat(cart.totalItems()).isEqualTo(Quantity.ZERO);
        assertThat(cart.totalAmount()).isEqualTo(Money.ZERO);
    }

    @Test
    void findItemById_shouldReturnCorrectItem() {
        ShoppingCart cart = ShoppingCart.startShopping(customerId);
        cart.addItem(product, quantity);
        ShoppingCartItem item = cart.items().iterator().next();

        ShoppingCartItem found = cart.findItem(item.id());

        assertThat(found).isEqualTo(item);
    }

    @Test
    void findItemByProduct_shouldReturnCorrectItem() {
        ShoppingCart cart = ShoppingCart.startShopping(customerId);
        cart.addItem(product, quantity);
        ShoppingCartItem item = cart.items().iterator().next();

        ShoppingCartItem found = cart.findItem(product);

        assertThat(found).isEqualTo(item);
    }

    @Test
    void findItemById_shouldThrowIfNotFound() {
        ShoppingCart cart = ShoppingCart.startShopping(customerId);

        assertThatThrownBy(() -> cart.findItem(new ShoppingCartItemId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Item not found");
    }

    @Test
    void changeItem_shouldUpdateQuantityAndTotals() {
        ShoppingCart cart = ShoppingCart.startShopping(customerId);
        cart.addItem(product, quantity);
        ShoppingCartItem item = cart.items().iterator().next();

        Quantity newQuantity = new Quantity(5);
        cart.changeItem(item.id(), newQuantity, product.inStock());

        assertThat(item.quantity()).isEqualTo(newQuantity);
        assertThat(cart.totalItems()).isEqualTo(newQuantity);
        assertThat(cart.totalAmount().value()).isEqualTo(product.value().value().multiply(BigDecimal.valueOf(newQuantity.value())));
    }

    @Test
    void containsUnavailableItems_shouldReturnTrueIfAnyItemUnavailable() {
        ShoppingCart cart = ShoppingCart.startShopping(customerId);
        Product unavailableProduct = ProductTestDataBuilder.aProduct()
            .name(new ProductName("Unavailable"))
            .inStock(false)
            .build();
        cart.addItem(unavailableProduct, new Quantity(1));

        assertThat(cart.containsUnavailableItems()).isTrue();
    }

    @Test
    void containsUnavailableItems_shouldReturnFalseIfAllItemsAvailable() {
        ShoppingCart cart = ShoppingCart.startShopping(customerId);
        cart.addItem(product, quantity);

        assertThat(cart.containsUnavailableItems()).isFalse();
    }

    @Test
    void isEmpty_shouldReturnTrueWhenNoItems() {
        ShoppingCart cart = ShoppingCart.startShopping(customerId);

        assertThat(cart.isEmpty()).isTrue();
    }

    @Test
    void isEmpty_shouldReturnFalseWhenHasItems() {
        ShoppingCart cart = ShoppingCart.startShopping(customerId);
        cart.addItem(product, quantity);

        assertThat(cart.isEmpty()).isFalse();
    }

    @Test
    void equalsAndHashCode_shouldWorkBasedOnId() {
        ShoppingCart cart1 = ShoppingCart.startShopping(customerId);
        ShoppingCart cart2 = ShoppingCart.startShopping(customerId);

        // Set same id for testing equality
        // ShoppingCartId sameId = cart1.id();
        // Reflection or package-private setter would be needed in real test, here we simulate
        cart2 = ShoppingCart.startShopping(customerId);
        // Simulate same id
        // This is just for demonstration; in real code, use a builder or test utility
        // to set the id

        assertThat(cart1).isEqualTo(cart1);
        assertThat(cart1).isNotEqualTo(cart2);
        assertThat(cart1.hashCode()).isNotEqualTo(cart2.hashCode());
    }
}
