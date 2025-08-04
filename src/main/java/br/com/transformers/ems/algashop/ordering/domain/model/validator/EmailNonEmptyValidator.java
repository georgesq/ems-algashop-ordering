package br.com.transformers.ems.algashop.ordering.domain.model.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class EmailNonEmptyValidator implements ConstraintValidator<EmailNonEmpty, String> {

    private static EmailNonEmptyValidator instance;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return EmailValidator.getInstance().isValid(value);
    }

    public static EmailNonEmptyValidator getInstance() {
        if (Objects.isNull(instance)) {
            instance = new EmailNonEmptyValidator();
        }
        return instance;
    }

}
