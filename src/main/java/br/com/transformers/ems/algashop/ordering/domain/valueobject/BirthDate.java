package br.com.transformers.ems.algashop.ordering.domain.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.exception.BirthDateException;

import java.time.LocalDate;
import java.util.Objects;

public record BirthDate(
        LocalDate value
) {

    public BirthDate(LocalDate value) {
        try {
            Objects.requireNonNull(value);
        } catch (NullPointerException e) {
            throw new BirthDateException(value);
        }

        if (LocalDate.now().isBefore(value)) {
            throw new BirthDateException(value);
        }

        this.value = value;
    }

    public Integer age() {
        return LocalDate.now().getYear() - this.value.getYear();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
