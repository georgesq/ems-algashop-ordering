package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.model.validator.NotNullNonEmptyValidator;

public record Quantity(

    Long value

) implements Comparable<Long> {
    
    private static NotNullNonEmptyValidator NNNEV = NotNullNonEmptyValidator.getInstance();
    
    public static final Quantity ZERO = new Quantity(0L);
    public static final Quantity DEZ = new Quantity(10L);


    public Quantity(Long value) {
        if (!NNNEV.isValid(value, null) || value.compareTo(-1L) <= 0) {
            throw new IllegalArgumentException();
        }

        this.value = value;
    }

    @Override
    public int compareTo(Long value) {
        return this.value.compareTo(value);
    }

    @Override
    public final String toString() {
        return this.value.toString();
    }

}
