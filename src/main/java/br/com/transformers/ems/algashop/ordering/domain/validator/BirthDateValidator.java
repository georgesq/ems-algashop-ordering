package br.com.transformers.ems.algashop.ordering.domain.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value.isBefore(LocalDate.now());
    }

}
