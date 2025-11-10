package com.algaworks.algashop.ordering.presentation;

public class GatewayTimeoutException extends RuntimeException {
    public GatewayTimeoutException(String message) {
        super(message);
    }

    public GatewayTimeoutException() {
    }
}
