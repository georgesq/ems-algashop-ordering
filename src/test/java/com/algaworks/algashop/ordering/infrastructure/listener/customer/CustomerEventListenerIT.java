package com.algaworks.algashop.ordering.infrastructure.listener.customer;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.algaworks.algashop.ordering.application.customer.loyaltypoints.CustomerLoyaltyPointsApplicationService;
import com.algaworks.algashop.ordering.application.notification.CustomerNotificationApplicationService;
import com.algaworks.algashop.ordering.application.notification.CustomerNotificationApplicationService.NotifyNewRegistrationInput;
import com.algaworks.algashop.ordering.domain.model.commons.Email;
import com.algaworks.algashop.ordering.domain.model.commons.FullName;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerArchivedEvent;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerRegisteredEvent;
import com.algaworks.algashop.ordering.domain.model.order.OrderId;
import com.algaworks.algashop.ordering.domain.model.order.OrderReadyEvent;

@SpringBootTest
public class CustomerEventListenerIT {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @MockitoSpyBean
    private CustomerEventListener customerEventListener;

    @MockitoBean
    private CustomerLoyaltyPointsApplicationService customerLoyaltyPointsApplicationService;

    @MockitoSpyBean
    private CustomerNotificationApplicationService customerNotificationApplicationService;

    @Test
    void shouldListenReadyEvent() {
        applicationEventPublisher.publishEvent(
            new OrderReadyEvent(new OrderId(), new CustomerId(), OffsetDateTime.now())
        );

        Mockito.verify(this.customerEventListener).listen(Mockito.any(OrderReadyEvent.class));

        Mockito.verify(this.customerLoyaltyPointsApplicationService).addLoyaltyPoints(Mockito.any(UUID.class), Mockito.anyString());

    }

    @Test
    void shouldListenCustomerRegisteredEvent() {

        applicationEventPublisher.publishEvent(
            new CustomerRegisteredEvent(
                new CustomerId(), 
                OffsetDateTime.now(), 
                new FullName("fn", "ln"), 
                new Email("a@a.com"))
        );

        Mockito.verify(this.customerEventListener).listen(Mockito.any(CustomerRegisteredEvent.class));

        Mockito.verify(this.customerNotificationApplicationService).notifyNewRegistration(Mockito.any(NotifyNewRegistrationInput.class));

    }

    @Test
    void shouldListenCustomerArchivedEvent() {
        
        applicationEventPublisher.publishEvent(
            new CustomerArchivedEvent(
                new CustomerId(), 
                OffsetDateTime.now())
        );

        Mockito.verify(this.customerEventListener).listen(Mockito.any(CustomerArchivedEvent.class));

    }
}
