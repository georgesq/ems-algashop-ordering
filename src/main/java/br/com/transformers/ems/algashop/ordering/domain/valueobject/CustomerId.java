package br.com.transformers.ems.algashop.ordering.domain.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.utility.IdGenerator;
import br.com.transformers.ems.algashop.ordering.domain.validator.NotNullNonEmpty;

import java.util.UUID;

public record CustomerId(
        @NotNullNonEmpty
        UUID value
) {
    public CustomerId() {
        this(IdGenerator.generate());
    }

    public CustomerId(UUID value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
