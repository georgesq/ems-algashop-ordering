package com.algaworks.algashop.ordering.application.customer.management;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.domain.model.customer.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.customer.service.CustomerLoyaltyPointsService;
import com.algaworks.algashop.ordering.domain.model.customer.valueobject.CustomerId;
import com.algaworks.algashop.ordering.domain.model.order.exception.OrderNotFoundException;
import com.algaworks.algashop.ordering.domain.model.order.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.order.valueobject.OrderId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerLoyaltyPointsApplicationService {
    
    private final CustomerLoyaltyPointsService customerLoyaltyPointsService;
    private final Customers customers;
    private final Orders orders;

    @Transactional
    public void addLoyaltyPoints(UUID rawCustomerId, String rawOrderId) {

        Objects.requireNonNull(rawCustomerId);
        Objects.requireNonNull(rawOrderId);

        var customer = this.customers.ofId(new CustomerId(rawCustomerId)).orElseThrow(() -> new CustomerNotFoundException());
        var order = this.orders.ofId(new OrderId(rawOrderId)).orElseThrow(() -> new OrderNotFoundException());

        this.customerLoyaltyPointsService.addPoints(customer, order);

        this.customers.add(customer);

    }

}
