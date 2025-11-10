package com.algaworks.algashop.ordering.application.order.notification;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface OrderNotificationService {

    void notifyPlaced(NotifyPlacedInput input);

    void notifyPaid(NotifyPaidInput input);

    void notifyReady(NotifyReadyInput input);

    public record NotifyPlacedInput(

        Long orderId,
        UUID customerId,
        OffsetDateTime placedAt

    ) {

    }

    public record NotifyPaidInput(

        Long orderId,
        UUID customerId,
        OffsetDateTime plaidAt

    ) {

    }

    public record NotifyReadyInput(

        Long orderId,
        UUID customerId,
        OffsetDateTime readyAt

    ) {

    }
}
