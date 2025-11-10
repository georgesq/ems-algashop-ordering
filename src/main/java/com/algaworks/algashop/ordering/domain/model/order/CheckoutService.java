package com.algaworks.algashop.ordering.domain.model.order;

import java.util.Set;

import com.algaworks.algashop.ordering.domain.model.DomainService;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartCantProceedToCheckoutException;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartItem;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class CheckoutService {

	private final CustomerHaveFreeShippingSpecification hasFreeShipping;

	public Order checkout(
			Customer customer,
			ShoppingCart shoppingCart,
			Billing billing,
			Shipping shipping,
			PaymentMethod paymentMethod) {

		if (shoppingCart.isEmpty()) {
			throw new ShoppingCartCantProceedToCheckoutException();
		}

		if (shoppingCart.containsUnavailableItems()) {
			throw new ShoppingCartCantProceedToCheckoutException();
		}

		Set<ShoppingCartItem> items = shoppingCart.items();

		Order order = Order.draft(shoppingCart.customerId());
		order.changeBilling(billing);
		order.changePaymentMethod(paymentMethod);

		if (this.hasFreeShipping.isSatisfiedBy(customer)) {

			Shipping freeShipping = shipping.toBuilder().cost(Money.ZERO).build();
			order.changeShipping(freeShipping);

		} else {

			order.changeShipping(shipping);

		}
		for (ShoppingCartItem item : items) {
			order.addItem(new Product(item.productId(), item.name(),
					item.price(), item.isAvailable()), item.quantity());
		}

		order.place();
		shoppingCart.empty();

		return order;
	}

}
