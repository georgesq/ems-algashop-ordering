package com.algaworks.algashop.ordering.infrastructure.listener.shoppingCart;

import java.util.Objects;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algashop.ordering.application.shoppingcart.notification.ShoppingCartNotificationService;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartCreatedEvent;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartItemAddedEvent;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartEmptiedEvent;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartItemRemovedEvent;
import com.algaworks.algashop.ordering.infrastructure.notification.shoppingcart.ShoppingCartNotificationServiceFakeImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ShoppingCartEventListener {

    private final ShoppingCartNotificationServiceFakeImpl notificationService;

    @EventListener
    public void listen(ShoppingCartItemAddedEvent event) {

        Objects.requireNonNull(event);
        
        this.notificationService.notifyNewItem(new ShoppingCartNotificationService.NotifyNewItemInput(
            event.shoppingCartId().value(),
            event.customerId().value(), 
            event.productId().value(),
            event.addedAt()
        ));

    }

    @EventListener
    public void listen(ShoppingCartCreatedEvent event) {

        Objects.requireNonNull(event);
        
        this.notificationService.notifyNewShoppingCart(new ShoppingCartNotificationService.NotifyNewShoppinCartInput(
            event.shoppingCartId().value(),
            event.customerId().value(), 
            event.createdAt()
        ));

    }
    
    @EventListener
    public void listen(ShoppingCartItemRemovedEvent event) {

        Objects.requireNonNull(event);
        
        this.notificationService.notifyItemRemoved(new ShoppingCartNotificationService.NotifyItemRemovedInput(
            event.shoppingCartId().value(),
            event.customerId().value(), 
            event.productId().value(),
            event.removedAt()
        ));

    }

    @EventListener
    public void listen(ShoppingCartEmptiedEvent event) {

        Objects.requireNonNull(event);
        
        this.notificationService.notifyItemEmpty(new ShoppingCartNotificationService.NotifyItemEmptyInput(
            event.shoppingCartId().value(),
            event.customerId().value(), 
            event.emptiedAt()
        ));

    }

}
