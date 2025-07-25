package br.com.transformers.ems.algashop.ordering.domain.validator;

import br.com.transformers.ems.algashop.ordering.domain.valueobject.ZipCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.Objects;

public class ZipCodeValidator implements ConstraintValidator<ZipCodeAnnotation, String> {

    private static ZipCodeValidator instance;

    public static ZipCodeValidator getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ZipCodeValidator();
        }

        return instance;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        NotNullNonEmptyValidator nnev = NotNullNonEmptyValidator.getInstance();

        return (nnev.isValid(value, null) && (8 == value.length() || 5 == value.length()));
    }

}
