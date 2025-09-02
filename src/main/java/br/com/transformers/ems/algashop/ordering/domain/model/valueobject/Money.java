package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money (

    BigDecimal value

) implements Comparable<Money> {
    
    public static final Money ZERO = new Money(BigDecimal.ZERO);
    public static final Money DEZ = new Money(BigDecimal.TEN);


    public Money(String value) {

        this(new BigDecimal(value));
        
    }

    public Money(BigDecimal value) {
        Objects.requireNonNull(value); //todo mensagem

        if (value.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException();
        }

        this.value = this.setConfiguration(value);
    }

    private BigDecimal setConfiguration(BigDecimal value) {
        return value.setScale(10, RoundingMode.HALF_EVEN);
    }

    public Money multiply(Quantity quantity) {
        if (quantity.value() < 1) {
            throw new IllegalArgumentException();
        }

        return new Money(this.value().multiply(BigDecimal.valueOf(quantity.value())));
    }

    public Money add(Money other) {
        return new Money(this.value().add(other.value()));
    }

    public Money divide(Money other) {
        return new Money(this.value().divide(other.value(), RoundingMode.HALF_EVEN));
    }    

    @Override
    public int compareTo(Money o) {

        return this.value.compareTo(o.value);
        
    }

    @Override
    public final String toString() {
        return this.value.toString();
    }
}
