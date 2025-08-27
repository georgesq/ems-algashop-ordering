package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.model.exception.LoyaltyPointException;

public record LoyaltyPoints(
        Integer value
) implements Comparable<LoyaltyPoints> {

    public static final LoyaltyPoints ZERO = new LoyaltyPoints(0);

    public LoyaltyPoints() {
        this(0);
    }

    public LoyaltyPoints(Integer value) {
        if (value < 0) {
            throw new LoyaltyPointException(value);
        }

        this.value = value;
    }

    private void valid(Integer value) {
        if (value <= 0) {
            throw new LoyaltyPointException(value);
        }
    }

    public LoyaltyPoints add(Integer value) {
        this.valid(value);

        return new LoyaltyPoints(this.value() + value);
    }

    @Override
    public String toString() {
        return this.value().toString();
    }

    @Override
    public int compareTo(LoyaltyPoints o) {
        return this.value().compareTo(o.value());
    }
}
