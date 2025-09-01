package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.model.validator.NotNullNonEmptyValidator;
import lombok.Builder;

public record Recipient(
    
        FullName fullName,
        Document document,
        Phone phone

) {

    static final NotNullNonEmptyValidator NNNEV = NotNullNonEmptyValidator.getInstance();

    @Builder(toBuilder = true)
    public Recipient(
            FullName fullName,
            Document document,
            Phone phone) {

        if (!NNNEV.isValid(fullName, null) || !NNNEV.isValid(document, null) || !NNNEV.isValid(phone, null)) {
            throw new IllegalArgumentException();
        }

        this.fullName = fullName;
        this.document = document;
        this.phone = phone;
    }

    @Override
    public final String toString() {
        return this.fullName + " " + this.document + " " + this.phone;
    }

}
