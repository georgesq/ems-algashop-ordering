package br.com.transformers.ems.algashop.ordering.domain.model.exception;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ProductId;

public class UnavailableProductException extends DomainException {

    public UnavailableProductException(ProductId id) {
        super(String.format("Unavailable Product %s", id));
    }

}
