package br.com.transformers.ems.algashop.ordering.domain.model.exception;

public class ZipCodeException extends DomainException {

    public ZipCodeException() {
        super("ZipCode invalid");
    }
}
