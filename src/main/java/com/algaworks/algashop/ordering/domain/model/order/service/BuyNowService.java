package com.algaworks.algashop.ordering.domain.model.order.service;

import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.customer.valueobject.CustomerId;
import com.algaworks.algashop.ordering.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.domain.model.order.entity.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.order.shipping.valueobject.Billing;
import com.algaworks.algashop.ordering.domain.model.order.shipping.valueobject.Shipping;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.utility.DomainService;

@DomainService
public class BuyNowService {

	public Order buyNow(Product product,
						CustomerId customerId,
						Billing billing,
						Shipping shipping,
						Quantity quantity,
						PaymentMethod paymentMethod) {
		
		product.checkOutOfStock();

		Order order = Order.draft(customerId);
		order.changeBilling(billing);
		order.changeShipping(shipping);
		order.changePaymentMethod(paymentMethod);
		order.addItem(product, quantity);
		order.place();

		return order;
	}

}