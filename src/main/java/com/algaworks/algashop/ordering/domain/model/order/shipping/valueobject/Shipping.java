package com.algaworks.algashop.ordering.domain.model.order.shipping.valueobject;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Address;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Money;

@Builder(toBuilder = true)
public record Shipping(Money cost, LocalDate expectedDate, Recipient recipient, Address address) {
	public Shipping {
		Objects.requireNonNull(address);
		Objects.requireNonNull(recipient);
		Objects.requireNonNull(cost);
		Objects.requireNonNull(expectedDate);
	}
}