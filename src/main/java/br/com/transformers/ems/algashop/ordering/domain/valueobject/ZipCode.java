package br.com.transformers.ems.algashop.ordering.domain.valueobject;

import br.com.transformers.ems.algashop.ordering.domain.exception.ZipCodeException;
import br.com.transformers.ems.algashop.ordering.domain.validator.ZipCodeValidator;

public record ZipCode(
        String value
) {

    public ZipCode(String value) {
        ZipCodeValidator zcv = ZipCodeValidator.getInstance();

        if (!zcv.isValid(value, null)) {
            throw new ZipCodeException();
        }

        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
