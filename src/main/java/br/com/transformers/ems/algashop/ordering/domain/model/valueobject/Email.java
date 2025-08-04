package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.model.exception.EmailException;
import br.com.transformers.ems.algashop.ordering.domain.model.validator.EmailNonEmptyValidator;

public record Email(
        String value
) {

    public Email(String value) {
        if (!EmailNonEmptyValidator.getInstance().isValid(value, null)) {
            throw new EmailException(value);
        }

        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
