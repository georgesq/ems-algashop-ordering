package com.algaworks.algashop.ordering.infrastructure.notification.order;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.algaworks.algashop.ordering.application.order.notification.OrderNotificationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderNotificationServiceFakeImpl implements OrderNotificationService {

    @Override
    public void notifyPlaced(NotifyPlacedInput input) {

        Objects.requireNonNull(input);

        log.info(input.toString());

    }

    @Override
    public void notifyPaid(NotifyPaidInput input) {

        Objects.requireNonNull(input);

        log.info(input.toString());
    }

    @Override
    public void notifyReady(NotifyReadyInput input) {

        Objects.requireNonNull(input);

        log.info(input.toString());
        
    }

}
