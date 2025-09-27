package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.Specification;
import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.LoyaltyPoints;

public class CustomerHaveFreeShippingSpecification implements Specification<Customer> {

    private final CustomerHasOrderedEnoughAtYearSpecification hasOrderedEnoughAtYear;

    private final CustomerHasEnougLoyaltyPointsSpecification hasEnoughBasicLoyaltyPoints;

    private final CustomerHasEnougLoyaltyPointsSpecification hasEnoughPremiumLoyaltyPoints;

    public CustomerHaveFreeShippingSpecification(Orders orders, 
            LoyaltyPoints basicLoyaltyPoints,
            long salesQuantityForFreeShipping, 
            LoyaltyPoints premiumLoyaltyPoints) {

        this.hasOrderedEnoughAtYear = new CustomerHasOrderedEnoughAtYearSpecification(orders, salesQuantityForFreeShipping);
        this.hasEnoughBasicLoyaltyPoints = new CustomerHasEnougLoyaltyPointsSpecification(basicLoyaltyPoints);
        this.hasEnoughPremiumLoyaltyPoints= new CustomerHasEnougLoyaltyPointsSpecification(premiumLoyaltyPoints);

    }


    @Override
    public boolean isSatisfiedBy(Customer customer) {

        return hasEnoughBasicLoyaltyPoints
                .and(hasOrderedEnoughAtYear)
                .or(hasEnoughPremiumLoyaltyPoints)
                .isSatisfiedBy(customer);
    }
    
}
