package com.algaworks.algashop.ordering.domain.model.order.service;

import java.util.Set;

import com.algaworks.algashop.ordering.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.domain.model.order.entity.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.order.shipping.valueobject.Billing;
import com.algaworks.algashop.ordering.domain.model.order.shipping.valueobject.Shipping;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.entity.ShoppingCartItem;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.exception.ShoppingCartCantProceedToCheckoutException;
import com.algaworks.algashop.ordering.domain.model.utility.DomainService;

@DomainService
public class CheckoutService {

	public Order checkout(ShoppingCart shoppingCart,
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
		order.changeShipping(shipping);
		order.changePaymentMethod(paymentMethod);

		for (ShoppingCartItem item : items) {
			order.addItem(new Product(item.productId(), item.name(),
				item.price(), item.isAvailable()), item.quantity());
		}

		order.place();
		shoppingCart.empty();

		return order;
	}

}
