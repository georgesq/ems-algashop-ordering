package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import java.time.LocalDate;

import br.com.transformers.ems.algashop.ordering.domain.model.exception.InvalidShippingDateException;
import br.com.transformers.ems.algashop.ordering.domain.model.validator.NotNullNonEmptyValidator;
import lombok.Builder;

public record Shipping(

        Money cost,
        LocalDate expectedDate,
        Address address,
        Recipient recipient

) {

    static final NotNullNonEmptyValidator NNNEV = NotNullNonEmptyValidator.getInstance();

    @Builder(toBuilder = true)
    public Shipping(
            Money cost,
            LocalDate expectedDate,
            Address address,
            Recipient recipient) {

        if (!NNNEV.isValid(cost, null) || !NNNEV.isValid(expectedDate, null) || !NNNEV.isValid(recipient, null) || !NNNEV.isValid(address, null)) {
            throw new IllegalArgumentException();
        }

        if (expectedDate.isBefore(LocalDate.now())) {
            throw new InvalidShippingDateException();
        }

        this.cost = cost;
        this.expectedDate = expectedDate;
        this.recipient = recipient;
        this.address = address;

    }

    @Override
    public final String toString() {
        return this.cost.toString() + " " + this.expectedDate.toString() + " " + this.recipient.toString() + " " + this.address.toString();
    }

}
