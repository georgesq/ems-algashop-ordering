package br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id;

import java.util.Objects;

import br.com.transformers.ems.algashop.ordering.domain.model.utility.IdGenerator;
import io.hypersistence.tsid.TSID;

public record ProductId(

    TSID value

) {

    public ProductId {
        Objects.requireNonNull(value);
    }

    public ProductId() {
        this(IdGenerator.generateTSID());
    }

    public ProductId(Long value) {
        this(TSID.from(value));
    }

    public ProductId(String value) {
        this(TSID.from(value));
    }

    @Override
    public final String toString() {
        return this.value().toString();
    }
}
