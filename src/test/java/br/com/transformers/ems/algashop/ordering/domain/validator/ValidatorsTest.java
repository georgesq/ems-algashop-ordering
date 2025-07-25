package br.com.transformers.ems.algashop.ordering.domain.validator;

import br.com.transformers.ems.algashop.ordering.domain.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.exception.*;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.*;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.BirthDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

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
