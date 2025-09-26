package com.algaworks.algashop.ordering.infrastructure.notification.shoppingcart;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.algaworks.algashop.ordering.application.shoppingcart.notification.ShoppingCartNotificationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShoppingCartNotificationServiceFakeImpl implements ShoppingCartNotificationService {

    @Override
    public void notifyNewShoppingCart(NotifyNewShoppinCartInput input) {

        Objects.requireNonNull(input);

        log.info(input.toString());

    }

    @Override
    public void notifyNewItem(NotifyNewItemInput input) {

        Objects.requireNonNull(input);

        log.info(input.toString());

    }

    @Override
    public void notifyItemRemoved(NotifyItemRemovedInput input) {

        Objects.requireNonNull(input);

        log.info(input.toString());

    }

    @Override
    public void notifyItemEmpty(NotifyItemEmptyInput input) {

        Objects.requireNonNull(input);

        log.info(input.toString());

    }

}
