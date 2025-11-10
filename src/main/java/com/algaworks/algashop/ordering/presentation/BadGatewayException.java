package com.algaworks.algashop.ordering.presentation;

public class BadGatewayException extends RuntimeException {
    
    public BadGatewayException(String message) {
        super(message);
    }

    public BadGatewayException() {
    }

    public BadGatewayException(String message, Exception e) {
        super(message, e);
    }
}
