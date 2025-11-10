package com.algaworks.algashop.ordering.infrastructure.listener.order;

import java.util.Objects;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algashop.ordering.application.order.notification.OrderNotificationService;
import com.algaworks.algashop.ordering.domain.model.order.OrderPaidEvent;
import com.algaworks.algashop.ordering.domain.model.order.OrderPlacedEvent;
import com.algaworks.algashop.ordering.domain.model.order.OrderReadyEvent;
import com.algaworks.algashop.ordering.infrastructure.notification.order.OrderNotificationServiceFakeImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventListener {
    
    private final OrderNotificationServiceFakeImpl orderNotificationService;

    @EventListener
    public void listenPlacedEvent(OrderPlacedEvent event) {

        Objects.requireNonNull(event);
        
        this.orderNotificationService.notifyPlaced(new OrderNotificationService.NotifyPlacedInput(
            event.orderId().value().toLong(),
            event.customerId().value(), 
            event.placedAt()
        ));

    }

    @EventListener
    public void listenPaidEvent(OrderPaidEvent event) {

        Objects.requireNonNull(event);
        
        this.orderNotificationService.notifyPaid(new OrderNotificationService.NotifyPaidInput(
            event.orderId().value().toLong(),
            event.customerId().value(), 
            event.paidAt()
        ));

    }

    @EventListener
    public void listenReadyEvent(OrderReadyEvent event) {

        Objects.requireNonNull(event);
        
        this.orderNotificationService.notifyReady(new OrderNotificationService.NotifyReadyInput(
            event.orderId().value().toLong(),
            event.customerId().value(), 
            event.readyAt()
        ));

    }

}
