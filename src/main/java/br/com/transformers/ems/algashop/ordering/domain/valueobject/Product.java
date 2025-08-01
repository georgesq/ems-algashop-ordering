package br.com.transformers.ems.algashop.ordering.domain.valueobject;

import java.util.Objects;

import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.ProductId;
import lombok.Builder;

@Builder
public record Product(
        ProductId id,
        ProductName name,
        Money value,
        Boolean inStock
) {

    public Product(
        ProductId id,
        ProductName name,
        Money value,
        Boolean inStock
    ) {
    
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);

        this.id = id;
        this.name = name;
        this.value = value;

        this.inStock = Objects.isNull(inStock) ? false : inStock;
        
    }

    public Boolean checkOutOfStock() {
        return !inStock;
    }

    @Override
    public final String toString() {

        return this.id() + " " + this.name() + " " + this.value().toString() + " " + this.inStock();

    }
}
