package br.com.transformers.ems.algashop.ordering.domain.exception;

public class PaidException extends DomainException {

    public PaidException() {
        super("Order is paid...");
    }
}
