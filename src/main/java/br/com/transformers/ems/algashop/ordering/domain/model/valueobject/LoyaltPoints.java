package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.model.exception.LoyaltyPointException;

public record LoyaltPoints(
        Integer value
) implements Comparable<LoyaltPoints> {
    public LoyaltPoints() {
        this(0);
    }

    public LoyaltPoints(Integer value) {
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

    public LoyaltPoints add(Integer value) {
        this.valid(value);

        return new LoyaltPoints(this.value() + value);
    }

    @Override
    public String toString() {
        return this.value().toString();
    }

    @Override
    public int compareTo(LoyaltPoints o) {
        return this.value().compareTo(o.value());
    }
}
