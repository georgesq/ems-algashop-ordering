package br.com.transformers.ems.algashop.ordering.domain.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.validator.NotNullNonEmptyValidator;

public record Quantity(

    Integer value

) implements Comparable<Integer> {
    
    private static NotNullNonEmptyValidator NNNEV = NotNullNonEmptyValidator.getInstance();
    
    public static final Quantity ZERO = new Quantity(0);


    public Quantity(Integer value) {
        if (!NNNEV.isValid(value, null) || value.compareTo(-1) < 0) {
            throw new IllegalArgumentException();
        }

        this.value = value;
    }

    @Override
    public int compareTo(Integer value) {
        return this.value.compareTo(value);
    }

    @Override
    public final String toString() {
        return this.value.toString();
    }

}
