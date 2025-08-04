package br.com.transformers.ems.algashop.ordering.domain.model.exception;

public class PaidException extends DomainException {

    public PaidException() {
        super("Order is paid...");
    }
}
