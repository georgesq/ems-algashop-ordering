package com.algaworks.algashop.ordering.infrastructure.listener.shoppingCart;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.product.ProductId;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartCreatedEvent;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartId;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartItemAddedEvent;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartEmptiedEvent;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartItemRemovedEvent;

@SpringBootTest
public class ShoppingCartEventListenerIT {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @MockitoSpyBean
    private ShoppingCartEventListener eventListener;

    @Test
    void shouldListenShoppingCartCreatedEvent() {

        applicationEventPublisher.publishEvent(
            new ShoppingCartCreatedEvent(
                new ShoppingCartId(), 
                new CustomerId(), 
                OffsetDateTime.now()
            )
        );

        Mockito.verify(this.eventListener).listen(Mockito.any(ShoppingCartCreatedEvent.class));

    }

    @Test
    void shouldListenShoppingCartItemAddedEvent() {

        applicationEventPublisher.publishEvent(
            new ShoppingCartItemAddedEvent(
                new ShoppingCartId(), 
                new CustomerId(), 
                new ProductId(),
                OffsetDateTime.now()
            )
        );

        Mockito.verify(this.eventListener).listen(Mockito.any(ShoppingCartItemAddedEvent.class));

    }

    @Test
    void shouldListenShoppingCartItemRemovedEvent() {

        applicationEventPublisher.publishEvent(
            new ShoppingCartItemRemovedEvent(
                new ShoppingCartId(), 
                new CustomerId(), 
                new ProductId(),
                OffsetDateTime.now()
            )
        );

        Mockito.verify(this.eventListener).listen(Mockito.any(ShoppingCartItemRemovedEvent.class));

    }

    @Test
    void shouldListenShoppingCartItemEmptiedEvent() {

        applicationEventPublisher.publishEvent(
            new ShoppingCartEmptiedEvent(
                new ShoppingCartId(), 
                new CustomerId(), 
                OffsetDateTime.now()
            )
        );

        Mockito.verify(this.eventListener).listen(Mockito.any(ShoppingCartEmptiedEvent.class));

    }

}
