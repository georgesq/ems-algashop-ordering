package br.com.transformers.ems.algashop.ordering.domain.exception;

public class DocumentException extends DomainException {

    public DocumentException() {
        super("Document is invalid");
    }
}
