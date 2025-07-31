package br.com.transformers.ems.algashop.ordering.domain.exception;

import br.com.transformers.ems.algashop.ordering.domain.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.ProductName;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.ProductId;

public record Product(
        ProductId id,
        ProductName name,
        Money value,
        Boolean inStock
) {

    public Boolean checkOutOfStock() {
        return !inStock;
    }

}
