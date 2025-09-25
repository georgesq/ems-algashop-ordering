package com.algaworks.algashop.ordering.infrastructure.notification.customer;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.algaworks.algashop.ordering.application.notification.CustomerNotificationApplicationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerNotificationServiceFakeImpl implements CustomerNotificationApplicationService {

    @Override
    public void notifyNewRegistration(NotifyNewRegistrationInput input) {

        Objects.requireNonNull(input);

        log.info("Welcome {}", input.fullName());
        log.info("User you email to access your account {}", input.email());

    }

}