package br.com.transformers.ems.algashop.ordering.domain.valueobject.id;

import java.util.Objects;

import br.com.transformers.ems.algashop.ordering.domain.utility.IdGenerator;
import io.hypersistence.tsid.TSID;

public record ShoppingCartItemId(

    TSID value

) {

    public ShoppingCartItemId {
        Objects.requireNonNull(value);
    }

    public ShoppingCartItemId() {
        this(IdGenerator.generateTSID());
    }
    
    public ShoppingCartItemId(Long value) {
        this(TSID.from(value));
    }

    public ShoppingCartItemId(String value) {
        this(TSID.from(value));
    }


    @Override
    public final String toString() {
        return this.value().toString();
    }
}
