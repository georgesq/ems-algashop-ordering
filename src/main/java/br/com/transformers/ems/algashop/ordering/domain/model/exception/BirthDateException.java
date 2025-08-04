package br.com.transformers.ems.algashop.ordering.domain.model.exception;

import java.time.LocalDate;

public class BirthDateException extends DomainException {

    public BirthDateException(LocalDate value) {
        super(String.format("Invalid birth date %s", value.toString()));
    }
}
