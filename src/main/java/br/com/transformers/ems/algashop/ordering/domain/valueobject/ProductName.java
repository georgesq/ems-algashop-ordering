package br.com.transformers.ems.algashop.ordering.domain.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.validator.NotNullNonEmptyValidator;

public record ProductName(

    String value

) {
    
    private static NotNullNonEmptyValidator NNNEV = NotNullNonEmptyValidator.getInstance();

    public ProductName(String value) {
        if (!NNNEV.isValid(value, null)) {
            throw new IllegalArgumentException();
        }

        this.value = value;
    }

    @Override
    public final String toString() {
        return this.value();
    }
    
}
