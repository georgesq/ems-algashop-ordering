package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.model.exception.DocumentException;

import java.util.Objects;

public record Document(
        String value
) {

    public Document(String value) {
        try {
            this.value = Objects.requireNonNull(value);
        } catch (NullPointerException e) {
            throw new DocumentException();
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
