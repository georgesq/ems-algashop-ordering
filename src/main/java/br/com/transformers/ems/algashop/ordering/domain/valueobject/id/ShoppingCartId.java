package br.com.transformers.ems.algashop.ordering.domain.valueobject.id;

import java.util.Objects;

import br.com.transformers.ems.algashop.ordering.domain.utility.IdGenerator;
import io.hypersistence.tsid.TSID;

public record ShoppingCartId(

    TSID value

) {

    public ShoppingCartId {
        Objects.requireNonNull(value);
    }

    public ShoppingCartId() {
        this(IdGenerator.generateTSID());
    }

    public ShoppingCartId(Long value) {
        this(TSID.from(value));
    }

    public ShoppingCartId(String value) {
        this(TSID.from(value));
    }

    @Override
    public final String toString() {
        return this.value().toString();
    }
}
