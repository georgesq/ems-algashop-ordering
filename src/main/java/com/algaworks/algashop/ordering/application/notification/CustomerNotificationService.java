package com.algaworks.algashop.ordering.application.notification;

import java.util.UUID;

public interface CustomerNotificationService {

    void notifyNewRegistration(NotifyNewRegistrationInput input);
    
    public record NotifyNewRegistrationInput(
        UUID customerId,
        String fullName,
        String email
    ) {

    }
}
