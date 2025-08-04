package br.com.transformers.ems.algashop.ordering.domain.model.exception;

public class RegisteredAtException extends DomainException {

    public RegisteredAtException() {
        super("RegisterdAt invalid");
    }
}
