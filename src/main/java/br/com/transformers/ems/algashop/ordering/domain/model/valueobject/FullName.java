package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.model.exception.FullNameException;
import br.com.transformers.ems.algashop.ordering.domain.model.validator.NotNullNonEmptyValidator;

public record FullName(
        String firstName,
        String lastName
) {

    public FullName(
            String firstName,
            String lastName) {

        NotNullNonEmptyValidator nnev = NotNullNonEmptyValidator.getInstance();

        if (!nnev.isValid(firstName, null)) {
            throw new FullNameException(firstName);
        }
        if (!nnev.isValid(lastName, null)) {
            throw new FullNameException(lastName);
        }

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    @Override
    public String toString() {
        return firstName + " " +lastName;
    }
}
