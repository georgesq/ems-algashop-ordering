package br.com.transformers.ems.algashop.ordering.domain.exception;

import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.ProductId;

public class UnavailableProductException extends DomainException {

    public UnavailableProductException(ProductId id) {
        super(String.format("Unavailable Product %s", id));
    }

}
