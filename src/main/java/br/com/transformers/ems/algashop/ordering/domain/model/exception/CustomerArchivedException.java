package br.com.transformers.ems.algashop.ordering.domain.model.exception;

public class CustomerArchivedException extends DomainException {

    public CustomerArchivedException() {
        super("Customer is not changeble");
    }

}
