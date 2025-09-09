package com.algaworks.algashop.ordering.domain.model.order.shipping.valueobject;

import lombok.Builder;

import java.util.Objects;

import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Address;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Document;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Email;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Phone;

@Builder
public record Billing(FullName fullName, Document document, Phone phone, Email email, Address address) {
	public Billing {
		Objects.requireNonNull(fullName);
		Objects.requireNonNull(document);
		Objects.requireNonNull(phone);
		Objects.requireNonNull(email);
		Objects.requireNonNull(address);
	}
}