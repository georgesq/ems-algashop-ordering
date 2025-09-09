package com.algaworks.algashop.ordering.infrastructure.customer.persistence.disassembler;

import org.springframework.stereotype.Component;

import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Address;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Document;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Email;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Phone;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.ZipCode;
import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.valueobject.BirthDate;
import com.algaworks.algashop.ordering.domain.model.customer.valueobject.CustomerId;
import com.algaworks.algashop.ordering.domain.model.customer.valueobject.LoyaltyPoints;
import com.algaworks.algashop.ordering.infrastructure.customer.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.order.persistence.embeddable.AddressEmbeddable;

@Component
public class CustomerPersistenceEntityDisassembler {

	public Customer toDomainEntity(CustomerPersistenceEntity entity) {
		return Customer.existing()
				.id(new CustomerId(entity.getId()))
				.fullName(new FullName(entity.getFirstName(), entity.getLastName()))
				.birthDate(entity.getBirthDate() != null ? new BirthDate(entity.getBirthDate()) : null)
				.email(new Email(entity.getEmail()))
				.phone(new Phone(entity.getPhone()))
				.document(new Document(entity.getDocument()))
				.loyaltyPoints(new LoyaltyPoints(entity.getLoyaltyPoints()))
				.promotionNotificationsAllowed(entity.getPromotionNotificationsAllowed())
				.archived(entity.getArchived())
				.registeredAt(entity.getRegisteredAt())
				.archivedAt(entity.getArchivedAt())
				.address(toAddressValueObject(entity.getAddress()))
				.version(entity.getVersion())
				.build();
	}

	private Address toAddressValueObject(AddressEmbeddable address) {
		return Address.builder()
				.street(address.getStreet())
				.number(address.getNumber())
				.complement(address.getComplement())
				.neighborhood(address.getNeighborhood())
				.city(address.getCity())
				.state(address.getState())
				.zipCode(new ZipCode(address.getZipCode()))
				.build();
	}
}