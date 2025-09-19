package com.algaworks.algashop.ordering.infrastructure.listener.customer;

import java.util.Objects;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algashop.ordering.application.notification.CustomerNotificationService;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerArchivedEvent;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerRegisteredEvent;
import com.algaworks.algashop.ordering.infrastructure.notification.customer.CustomerNotificationServiceFakeImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerEventListener {

    private final CustomerNotificationServiceFakeImpl customerNotificationService;

    @EventListener
    public void listenRegisterEvent(CustomerRegisteredEvent event) {

        Objects.requireNonNull(event);
        
        this.customerNotificationService.notifyNewRegistration(new CustomerNotificationService.NotifyNewRegistrationInput(event.customerId().value(), event.fullName().toString(), 
            event.email().toString()));

    }

    @EventListener
    public void listenArchiverEvent(CustomerArchivedEvent event) {

        log.info(event.toString());

    }

}