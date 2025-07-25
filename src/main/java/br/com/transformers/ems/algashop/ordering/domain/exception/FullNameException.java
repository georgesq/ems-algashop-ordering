package br.com.transformers.ems.algashop.ordering.domain.exception;

public class FullNameException extends DomainException {

        public FullNameException(String value) {
            super(String.format("Invalid FullName %s", value));
        }
    }
