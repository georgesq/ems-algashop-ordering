package com.algaworks.algashop.ordering.domain.model.product.valueobject;

import com.algaworks.algashop.ordering.domain.model.commons.validator.FieldValidations;

public record ProductName(String value) {

	public ProductName {
		FieldValidations.requiresNonBlank(value);
	}

	@Override
	public String toString() {
		return value;
	}

}