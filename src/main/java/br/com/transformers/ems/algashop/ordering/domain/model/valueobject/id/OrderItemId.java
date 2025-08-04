package br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id;

import java.util.Objects;

import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;
import io.hypersistence.tsid.TSID;

public record OrderItemId(

    TSID value

) {

    public OrderItemId {
        Objects.requireNonNull(value);
    }

    public OrderItemId() {
        this(IdGenerator.generateTSID());
    }
    
    public OrderItemId(Long value) {
        this(TSID.from(value));
    }

    public OrderItemId(String value) {
        this(TSID.from(value));
    }


    @Override
    public final String toString() {
        return this.value().toString();
    }
}
