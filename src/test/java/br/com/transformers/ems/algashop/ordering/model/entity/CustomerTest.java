package br.com.transformers.ems.algashop.ordering.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Customer;
import br.com.transformers.ems.algashop.ordering.domain.model.exception.CustomerArchivedException;
import br.com.transformers.ems.algashop.ordering.domain.model.exception.LoyaltyPointException;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.FullName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Phone;
import br.com.transformers.ems.algashop.ordering.model.entity.databuilder.CustomerTestDataBuilder;

public class CustomerTest {

    Customer customer = CustomerTestDataBuilder.newInstance().build();

    @Test
    public void given_newpoints_then_loyaltyPoints_correct() {

        customer.addLoyaltyPoints(10);

        assertEquals(10, customer.loyaltyPoints());

        customer.addLoyaltyPoints(5);

        assertEquals(15, customer.loyaltyPoints());
    }

    @Test
    public void given_enable_promotion_notification_then_customer_enabled_promotion_notification() {
        customer.enablePromotionNotificafications();

        assertEquals(true, customer.isPromotionNotificaficationsAllowed());
    }

    @Test
    public void given_disable_promotion_notification_then_customer_disabled_promotion_notification() {
        customer.disablePromotionNotificafications();

        assertEquals(false, customer.isPromotionNotificaficationsAllowed());
    }

    @Test
    public void given_archive_then_customer_archived() {
        customer.archive();

        Assertions.assertWith(customer,
                c -> assertThat(c.isArchived()).isEqualTo(true),
                c -> assertThat(c.address().number()).isNull(),
                c -> assertThat(c.address().complement()).isNull()
        );
    }

    @Test
    public void given_archived_call_changeble_methods_then_throws_CustomerArchivedException() {

        customer.archive();

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::archive);

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeEmail(new Email("qq@coisa.com"))
                );

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeName(new FullName("firstName", "lastName"))
                );

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changePhone(new Phone("qqcoisa"))
                );

    }

    @Test
    public void given_invalid_point_then_throws_LoyaltyPointException() {

        assertThatExceptionOfType(LoyaltyPointException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(-1)
                );
        assertThatExceptionOfType(LoyaltyPointException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(0)
                );

    }
}
