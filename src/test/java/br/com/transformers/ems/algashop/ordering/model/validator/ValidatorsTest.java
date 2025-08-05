package br.com.transformers.ems.algashop.ordering.model.validator;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.exception.BirthDateException;
import br.com.transformers.ems.algashop.ordering.domain.model.exception.EmailException;
import br.com.transformers.ems.algashop.ordering.domain.model.exception.FullNameException;
import br.com.transformers.ems.algashop.ordering.domain.model.exception.ZipCodeException;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.BirthDate;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.FullName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ZipCode;

public class ValidatorsTest {

    @Test
    public void givenBirthDateInvalidThenThrowsBirthDateException() {

        Assertions.assertThatExceptionOfType(BirthDateException.class)
                .isThrownBy(() -> new BirthDate(LocalDate.ofYearDay(LocalDate.now().getYear() + 1, 1)));

    }

    @Test
    public void givenInvalidEmailThenThrowsEmailException() {

        Assertions.assertThatExceptionOfType(EmailException.class)
                .isThrownBy(() -> new Email("emailemail.com"));

    }

    @Test
    public void givenInvalidFullNameThenThrowsFullNameException() {

        Assertions.assertThatExceptionOfType(FullNameException.class)
                .isThrownBy(() -> new FullName("fn", ""));

    }

    @Test
    public void givenInvalidZipCodeThenThrowsZipCodeException() {

        Assertions.assertThatExceptionOfType(ZipCodeException.class)
                .isThrownBy(() -> new ZipCode("41"));

    }
}
