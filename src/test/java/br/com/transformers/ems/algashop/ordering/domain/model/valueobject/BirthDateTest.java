package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BirthDateTest {

    @Test
    public void call_age_should_correct_return() {
        LocalDate ldtBD = LocalDate.now();

        var bd = new BirthDate(ldtBD.minusYears(5));

        Assertions.assertThat(LocalDate.now().getYear() - ldtBD.minusYears(5).getYear()).isEqualTo(bd.age());
    }

}