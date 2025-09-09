package com.algaworks.algashop.ordering.domain.model.commons.valueobject;

import com.algaworks.algashop.ordering.domain.model.commons.validator.FieldValidations;

public record Email(String value) {
	public Email {
		FieldValidations.requiresValidEmail(value);
	}

	@Override
	public String toString() {
		return value;
	}
}