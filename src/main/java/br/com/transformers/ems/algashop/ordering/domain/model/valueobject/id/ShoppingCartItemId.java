package br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id;

import java.util.Objects;
import java.util.UUID;

import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;

public record ShoppingCartItemId(

    UUID value

) {

    public ShoppingCartItemId {
        Objects.requireNonNull(value);
    }

    public ShoppingCartItemId() {
        this(IdGenerator.generateUUID());
    }
    
    public ShoppingCartItemId(String value) {
        this(UUID.fromString(value));
    }


    @Override
    public final String toString() {
        return this.value().toString();
    }
}
