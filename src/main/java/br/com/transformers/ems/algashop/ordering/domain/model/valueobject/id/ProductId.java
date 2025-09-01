package br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id;

import java.util.Objects;
import java.util.UUID;

import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;

public record ProductId(

    UUID value

) {

    public ProductId {
        Objects.requireNonNull(value);
    }

    public ProductId() {
        this(IdGenerator.generateUUID());
    }

    public ProductId(String value) {
        this(UUID.fromString(value));
    }

    @Override
    public final String toString() {
        return this.value().toString();
    }
}
