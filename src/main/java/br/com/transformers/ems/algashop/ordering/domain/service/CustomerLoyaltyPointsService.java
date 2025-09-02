package br.com.transformers.ems.algashop.ordering.domain.service;

import java.util.Objects;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.LoyaltyPoints;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;

public class CustomerLoyaltyPointsService {

    private static final LoyaltyPoints basePoints = new LoyaltyPoints(5);

    private static final Money expedtedAmountToGivePoints = new Money("1000");

    public void addPoints(Customer customer, Order order) {

        validAddPoints(customer, order);

        customer.addLoyaltyPoints(calculatePoints(order));

    }

    private LoyaltyPoints calculatePoints(Order order) {

        if (shouldGivePointsByAmount(order.totalAmount())) {
            
            Money result = order.totalAmount().divide(expedtedAmountToGivePoints);

            return new LoyaltyPoints(result.value().intValue() * basePoints.value());

        }
        
        return LoyaltyPoints.ZERO;

    }

    private boolean shouldGivePointsByAmount(Money amount) {

        return amount.compareTo(expedtedAmountToGivePoints) >= 0;

    }

    private void validAddPoints(Customer customer, Order order) {
        Objects.requireNonNull(customer);
        Objects.requireNonNull(order);

        if (!customer.id().equals(order.customerId())) {
            throw new IllegalArgumentException("Order does not belong to the customer");
        }

        if (!order.isReady()) {
            throw new IllegalArgumentException("Order is not in a status that allows adding points");
        }
    }


}