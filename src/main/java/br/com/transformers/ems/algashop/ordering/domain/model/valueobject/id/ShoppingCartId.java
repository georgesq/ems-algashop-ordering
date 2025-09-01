package br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id;

import java.util.Objects;
import java.util.UUID;

import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;

public record ShoppingCartId(

    UUID value

) {

    public ShoppingCartId {
        Objects.requireNonNull(value);
    }

    public ShoppingCartId() {
        this(IdGenerator.generateUUID());
    }

    @Override
    public final String toString() {
        return this.value().toString();
    }
}
