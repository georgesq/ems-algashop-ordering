package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.model.validator.NotNullNonEmptyValidator;
import lombok.Builder;

public record Address(
        String street,
        Integer number,
        String complement,
        String neighborhood,
        String city,
        String state,
        ZipCode zipCode
) {

    @Builder(toBuilder = true)
    public Address(String street, Integer number, String complement, String neighborhood, String city, String state,
                   ZipCode zipCode) {
        NotNullNonEmptyValidator nnev = NotNullNonEmptyValidator.getInstance();

        nnev.isValid(street, null);
        this.street = street;

        nnev.isValid(street, null);
        this.number = number;

        nnev.isValid(street, null);
        this.complement = complement;

        nnev.isValid(street, null);
        this.neighborhood = neighborhood;

        nnev.isValid(street, null);
        this.city = city;

        nnev.isValid(street, null);
        this.state = state;

        this.zipCode = zipCode;
    }

}
