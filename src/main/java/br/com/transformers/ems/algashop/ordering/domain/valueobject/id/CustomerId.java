package br.com.transformers.ems.algashop.ordering.domain.valueobject.id;

import java.util.UUID;

import br.com.transformers.ems.algashop.ordering.domain.utility.IdGenerator;
import br.com.transformers.ems.algashop.ordering.domain.validator.NotNullNonEmptyValidator;

public record CustomerId(
        UUID value
) {

    static final NotNullNonEmptyValidator NNNEV = NotNullNonEmptyValidator.getInstance();

    public CustomerId() {
        this(IdGenerator.generateUUID());
    }

    public CustomerId(UUID value) {
        if (!NNNEV.isValid(value, null)) {
            throw new IllegalArgumentException();
        }
        
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
