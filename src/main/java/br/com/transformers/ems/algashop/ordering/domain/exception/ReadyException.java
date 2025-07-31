package br.com.transformers.ems.algashop.ordering.domain.exception;

public class ReadyException extends DomainException {

    public ReadyException() {
        super("Order is readied or paid...");
    }
}
