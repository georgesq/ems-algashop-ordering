package br.com.transformers.ems.algashop.ordering.domain.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) // Apply to fields and method parameters
@Retention(RetentionPolicy.RUNTIME) // Available at runtime for validation
@Constraint(validatedBy = EmailNonEmptyValidator.class) // Link to the validator class
public @interface EmailNonEmpty {
    String message() default "Invalid email"; // Default error message
    Class<?>[] groups() default {}; // For validation groups
    Class<? extends Payload>[] payload() default {}; // For carrying payload information
}
