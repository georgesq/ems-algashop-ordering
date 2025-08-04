package br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id;

import java.util.Objects;

import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;
import io.hypersistence.tsid.TSID;

public record OrderId(

    TSID value

) {

    public OrderId {
        Objects.requireNonNull(value);
    }

    public OrderId() {
        this(IdGenerator.generateTSID());
    }

    public OrderId(Long value) {
        this(TSID.from(value));
    }

    public OrderId(String value) {
        this(TSID.from(value));
    }

    @Override
    public final String toString() {
        return this.value().toString();
    }
}
