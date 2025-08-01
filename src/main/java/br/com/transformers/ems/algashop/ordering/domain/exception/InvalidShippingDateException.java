package br.com.transformers.ems.algashop.ordering.domain.exception;

public class InvalidShippingDateException extends DomainException {

    public InvalidShippingDateException() {
        super("Invalid Shipping Date");
    }

}
