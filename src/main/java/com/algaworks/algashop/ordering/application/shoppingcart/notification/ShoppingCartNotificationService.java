package com.algaworks.algashop.ordering.application.shoppingcart.notification;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface ShoppingCartNotificationService {

    void notifyNewShoppingCart(NotifyNewShoppinCartInput input);

    void notifyNewItem(NotifyNewItemInput input);

    void notifyItemRemoved(NotifyItemRemovedInput input);

    void notifyItemEmpty(NotifyItemEmptyInput input);

    public record NotifyNewShoppinCartInput(

            UUID shoppingCartId,
            UUID customerId,
            OffsetDateTime createdAt) {

    }

    public record NotifyItemRemovedInput(

            UUID shoppingCartId,
            UUID customerId,
            UUID productId,
            OffsetDateTime removedAt) {

    }

    public record NotifyNewItemInput(

            UUID shoppingCartId,
            UUID customerId,
            UUID productId,
            OffsetDateTime addedAt) {

    }

    public record NotifyItemEmptyInput(

            UUID shoppingCartId,
            UUID customerId,
            OffsetDateTime emptiedAt) {

    }

}
