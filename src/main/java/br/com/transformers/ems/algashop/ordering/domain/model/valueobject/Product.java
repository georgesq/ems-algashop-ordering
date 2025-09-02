package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import java.util.Objects;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ProductId;
import lombok.Builder;

@Builder
public record Product(
        ProductId id,
        ProductName name,
        Money price,
        Boolean inStock
) {

    public Product(
        ProductId id,
        ProductName name,
        Money price,
        Boolean inStock
    ) {
    
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(price);

        this.id = id;
        this.name = name;
        this.price = price;

        this.inStock = Objects.isNull(inStock) ? false : inStock;
        
    }

    public Boolean checkOutOfStock() {
        return !inStock;
    }

    @Override
    public final String toString() {

        return this.id() + " " + this.name() + " " + this.price().toString() + " " + this.inStock();

    }
}
