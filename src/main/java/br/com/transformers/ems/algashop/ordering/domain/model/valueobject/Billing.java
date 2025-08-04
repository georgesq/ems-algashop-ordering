package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.model.validator.NotNullNonEmptyValidator;
import lombok.Builder;

public record Billing(

        FullName fullName,
        Document document,
        Phone phone,
        Address address,
        Email email

) {

    static final NotNullNonEmptyValidator NNNEV = NotNullNonEmptyValidator.getInstance();

    @Builder(toBuilder = true)
    public Billing(
            FullName fullName,
            Document document,
            Phone phone,
            Address address,
            Email email) {

        if (!NNNEV.isValid(fullName, null) || !NNNEV.isValid(document, null) || !NNNEV.isValid(phone, null) || !NNNEV.isValid(address, null) || !NNNEV.isValid(email, null)) {
            throw new IllegalArgumentException();
        }

        this.fullName = fullName;
        this.document = document;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    @Override
    public final String toString() {
        return this.fullName + " " + this.document + " " + this.phone + " "+ this.address;
    }

}
