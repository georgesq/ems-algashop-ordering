package br.com.transformers.ems.algashop.ordering.domain.valueobject;

import java.time.LocalDate;

import br.com.transformers.ems.algashop.ordering.domain.exception.InvalidShippingDateException;
import br.com.transformers.ems.algashop.ordering.domain.validator.NotNullNonEmptyValidator;
import lombok.Builder;

public record Shipping(

        Money cost,
        LocalDate expectedDate,
        Recipient recipient

) {

    static final NotNullNonEmptyValidator NNNEV = NotNullNonEmptyValidator.getInstance();

    @Builder(toBuilder = true)
    public Shipping(
            Money cost,
            LocalDate expectedDate,
            Recipient recipient) {

        if (!NNNEV.isValid(cost, null) || !NNNEV.isValid(expectedDate, null) || !NNNEV.isValid(recipient, null)) {
            throw new IllegalArgumentException();
        }

        if (expectedDate.isBefore(LocalDate.now())) {
            throw new InvalidShippingDateException();
        }

        this.cost = cost;
        this.expectedDate = expectedDate;
        this.recipient = recipient;

    }

    @Override
    public final String toString() {
        return this.cost.toString() + " " + this.expectedDate.toString() + " " + this.recipient.toString();
    }

}
