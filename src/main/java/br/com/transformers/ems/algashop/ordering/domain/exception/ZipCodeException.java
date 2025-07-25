package br.com.transformers.ems.algashop.ordering.domain.exception;

public class ZipCodeException extends DomainException {

    public ZipCodeException() {
        super("ZipCode invalid");
    }
}
