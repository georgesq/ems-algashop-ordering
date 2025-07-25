package br.com.transformers.ems.algashop.ordering.domain.exception;

public class EmailException extends DomainException {

    public EmailException(String value) {
        super(String.format("Invalid email %s", value));
    }
}
