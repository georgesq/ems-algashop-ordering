package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.DomainService;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.product.Product;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class BuyNowService {

	private final CustomerHaveFreeShippingSpecification customerHaveFreeShippingSpecification;

	public Order buyNow(Product product,
						Customer customer,
						Billing billing,
						Shipping shipping,
						Quantity quantity,
						PaymentMethod paymentMethod) {
		
		product.checkOutOfStock();

		Order order = Order.draft(customer.id());
		order.changeBilling(billing);
		order.changePaymentMethod(paymentMethod);

		if (this.customerHaveFreeShippingSpecification.isSatisfied(customer)) {

			Shipping freeShipping = shipping.toBuilder().cost(Money.ZERO).build();
			order.changeShipping(freeShipping);

		} else {
			
			order.changeShipping(shipping);

		}

		order.addItem(product, quantity);
		order.place();

		return order;
	}


}