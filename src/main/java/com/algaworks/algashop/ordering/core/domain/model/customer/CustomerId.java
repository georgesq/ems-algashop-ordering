package com.algaworks.algashop.ordering.core.domain.model.customer;

import java.util.Objects;
import java.util.UUID;

import com.algaworks.algashop.ordering.core.domain.model.IdGenerator;

public record CustomerId(UUID value) {

    public CustomerId {
        Objects.requireNonNull(value);
    }

    public CustomerId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
