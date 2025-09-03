package com.algaworks.algashop.ordering.domain.model.valueobject;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages;

public record BirthDate(LocalDate value) {

	public BirthDate {
		Objects.requireNonNull(value);
		if (value.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException(ErrorMessages.VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST);
		}
	}
	
	public Integer age() {
		return Period.between(this.value, LocalDate.now()).getYears();
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
