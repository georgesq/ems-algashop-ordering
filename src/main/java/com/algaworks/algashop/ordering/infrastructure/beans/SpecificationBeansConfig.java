package com.algaworks.algashop.ordering.infrastructure.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algashop.ordering.domain.model.customer.LoyaltyPoints;
import com.algaworks.algashop.ordering.domain.model.order.CustomerHaveFreeShippingSpecification;
import com.algaworks.algashop.ordering.domain.model.order.Orders;

@Configuration
public class SpecificationBeansConfig {

    @Bean
    public CustomerHaveFreeShippingSpecification customerHaveFreeShippingSpecification(Orders orders) {

        return new CustomerHaveFreeShippingSpecification(
                orders,
                new LoyaltyPoints(200),
                2,
                new LoyaltyPoints(2000));

    }

}
