package com.algaworks.algashop.ordering.domain.model.commons.valueobject;

import lombok.Builder;

import java.util.Objects;

import com.algaworks.algashop.ordering.domain.model.commons.validator.FieldValidations;

public record Address(
        String street,
        String complement,
        String neighborhood,
        String number,
        String city,
        String state,
        ZipCode zipCode) {
    @Builder(toBuilder = true)
    public Address(String street, String complement, String neighborhood, String number, String city, String state,
            ZipCode zipCode) {

        FieldValidations.requiresNonBlank(street);
        FieldValidations.requiresNonBlank(neighborhood);
        FieldValidations.requiresNonBlank(city);
        FieldValidations.requiresNonBlank(number);
        FieldValidations.requiresNonBlank(state);
        Objects.requireNonNull(zipCode);

        this.street = street;

        this.number = number;

        this.complement = complement;

        this.neighborhood = neighborhood;

        this.city = city;

        this.state = state;

        this.zipCode = zipCode;

    }
}
