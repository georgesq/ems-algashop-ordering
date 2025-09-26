package com.algaworks.algashop.ordering.domain.model.order;

import java.time.Year;

import com.algaworks.algashop.ordering.domain.model.Specification;
import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.LoyaltyPoints;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerHaveFreeShippingSpecification implements Specification<Customer> {

    private final Orders orders;
    private final int minPointsForFreeShippingRules2;
    private final int salesQuantityForFreeShippingRules;
    private final int minPointsForFreeShippingRules1;


    @Override
    public boolean isSatisfied(Customer customer) {

		return customer.loyaltyPoints().compareTo(new LoyaltyPoints(minPointsForFreeShippingRules1)) >= 0
			&& this.orders.salesQuantityByCustomerInYear(customer.id(), Year.now()) >= salesQuantityForFreeShippingRules
			|| customer.loyaltyPoints().compareTo(new LoyaltyPoints(minPointsForFreeShippingRules2)) >= 0;

    }
    
}
