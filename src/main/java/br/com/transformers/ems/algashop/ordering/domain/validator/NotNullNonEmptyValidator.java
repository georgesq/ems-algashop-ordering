package br.com.transformers.ems.algashop.ordering.domain.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class NotNullNonEmptyValidator implements ConstraintValidator<NotNullNonEmpty, Object> {

    private static NotNullNonEmptyValidator instance;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return !Objects.isNull(value) && (!(value instanceof String) || !((String) value).isBlank());
    }

    public static NotNullNonEmptyValidator getInstance() {
        if (Objects.isNull(instance)) {
            instance = new NotNullNonEmptyValidator();
        }
        return instance;
    }

}
