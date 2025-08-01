package br.com.transformers.ems.algashop.ordering.domain.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.validator.NotNullNonEmptyValidator;
import lombok.Builder;

public record Recipient(
        FullName fullName,
        Document document,
        Phone phone,
        Address address

) {

    static final NotNullNonEmptyValidator NNNEV = NotNullNonEmptyValidator.getInstance();

    @Builder(toBuilder = true)
    public Recipient(
            FullName fullName,
            Document document,
            Phone phone,
            Address address) {

        if (!NNNEV.isValid(fullName, null) || !NNNEV.isValid(document, null) || !NNNEV.isValid(phone, null) || !NNNEV.isValid(address, null)) {
            throw new IllegalArgumentException();
        }

        this.fullName = fullName;
        this.document = document;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public final String toString() {
        return this.fullName + " " + this.document + " " + this.phone + " "+ this.address;
    }

}
