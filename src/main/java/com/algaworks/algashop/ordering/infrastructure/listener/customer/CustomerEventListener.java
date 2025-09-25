package com.algaworks.algashop.ordering.infrastructure.listener.customer;

import java.util.Objects;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algashop.ordering.application.customer.loyaltypoints.CustomerLoyaltyPointsApplicationService;
import com.algaworks.algashop.ordering.application.notification.CustomerNotificationApplicationService;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerArchivedEvent;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerRegisteredEvent;
import com.algaworks.algashop.ordering.domain.model.order.OrderReadyEvent;
import com.algaworks.algashop.ordering.infrastructure.notification.customer.CustomerNotificationServiceFakeImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerEventListener {

    private final CustomerNotificationServiceFakeImpl customerNotificationService;

    private final CustomerLoyaltyPointsApplicationService customerLoyaltyPointsApplicationService;

    @EventListener
    public void listen(CustomerRegisteredEvent event) {

        Objects.requireNonNull(event);
        
        this.customerNotificationService.notifyNewRegistration(new CustomerNotificationApplicationService.NotifyNewRegistrationInput(event.customerId().value(), event.fullName().toString(), 
            event.email().toString()));

    }

    @EventListener
    public void listen(CustomerArchivedEvent event) {

        log.info(event.toString());

    }

    @EventListener
    public void listen(OrderReadyEvent event) {

        this.customerLoyaltyPointsApplicationService.addLoyaltyPoints(event.customerId().value(), 
            event.orderId().toString());

    }

}