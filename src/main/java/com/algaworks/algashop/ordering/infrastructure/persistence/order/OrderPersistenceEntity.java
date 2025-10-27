package com.algaworks.algashop.ordering.infrastructure.persistence.order;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(of = "id")
@Table(name = "\"order\"")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class OrderPersistenceEntity
		extends AbstractAggregateRoot<CustomerPersistenceEntity> {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @JoinColumn
    @ManyToOne(optional = false)
    private CustomerPersistenceEntity customer;

    private BigDecimal totalAmount;
    private Integer totalItems;
    private String status;
    private String paymentMethod;

    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime readyAt;

    @CreatedBy
    private UUID createdByUserId;

    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;

    @LastModifiedBy
    private UUID lastModifiedByUserId;

    @Version
    private Long version;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "billing_first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "billing_last_name")),
            @AttributeOverride(name = "document", column = @Column(name = "billing_document")),
            @AttributeOverride(name = "phone", column = @Column(name = "billing_phone")),
            @AttributeOverride(name = "address.street", column = @Column(name = "billing_address_street")),
            @AttributeOverride(name = "address.number", column = @Column(name = "billing_address_number")),
            @AttributeOverride(name = "address.complement", column = @Column(name = "billing_address_complement")),
            @AttributeOverride(name = "address.neighborhood", column = @Column(name = "billing_address_neighborhood")),
            @AttributeOverride(name = "address.city", column = @Column(name = "billing_address_city")),
            @AttributeOverride(name = "address.state", column = @Column(name = "billing_address_state")),
            @AttributeOverride(name = "address.zipCode", column = @Column(name = "billing_address_zipCode"))
    })
    private BillingEmbeddable billing;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "cost", column = @Column(name = "shipping_cost")),
            @AttributeOverride(name = "expectedDate", column = @Column(name = "shipping_expected_date")),
            @AttributeOverride(name = "recipient.firstName", column = @Column(name = "shipping_recipient_first_name")),
            @AttributeOverride(name = "recipient.lastName", column = @Column(name = "shipping_recipient_last_name")),
            @AttributeOverride(name = "recipient.document", column = @Column(name = "shipping_recipient_document")),
            @AttributeOverride(name = "recipient.phone", column = @Column(name = "shipping_recipient_phone")),
            @AttributeOverride(name = "address.street", column = @Column(name = "shipping_address_street")),
            @AttributeOverride(name = "address.number", column = @Column(name = "shipping_address_number")),
            @AttributeOverride(name = "address.complement", column = @Column(name = "shipping_address_complement")),
            @AttributeOverride(name = "address.neighborhood", column = @Column(name = "shipping_address_neighborhood")),
            @AttributeOverride(name = "address.city", column = @Column(name = "shipping_address_city")),
            @AttributeOverride(name = "address.state", column = @Column(name = "shipping_address_state")),
            @AttributeOverride(name = "address.zipCode", column = @Column(name = "shipping_address_zipCode"))
    })
    private ShippingEmbeddable shipping;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItemPersistenceEntity> items = new HashSet<>();

    @Builder
    public OrderPersistenceEntity(Long id, CustomerPersistenceEntity customer, BigDecimal totalAmount, Integer totalItems, String status, String paymentMethod, OffsetDateTime placedAt, OffsetDateTime paidAt, OffsetDateTime canceledAt, OffsetDateTime readyAt, UUID createdByUserId, OffsetDateTime lastModifiedAt, UUID lastModifiedByUserId, Long version, BillingEmbeddable billing, ShippingEmbeddable shipping, Set<OrderItemPersistenceEntity> items) {
        this.id = id;
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.placedAt = placedAt;
        this.paidAt = paidAt;
        this.canceledAt = canceledAt;
        this.readyAt = readyAt;
        this.createdByUserId = createdByUserId;
        this.lastModifiedAt = lastModifiedAt;
        this.lastModifiedByUserId = lastModifiedByUserId;
        this.version = version;
        this.billing = billing;
        this.shipping = shipping;
        this.replaceItems(items);
    }

    public void replaceItems(Set<OrderItemPersistenceEntity> items) {
        if (items == null || items.isEmpty()) {
            this.setItems(new HashSet<>());
            return;
        }

        items.forEach(i -> i.setOrder(this));
        this.setItems(items);
    }

    public void addItem(OrderItemPersistenceEntity item) {
        if (item == null) {
            return;
        }

        if (this.getItems() == null) {
            this.setItems(new HashSet<>());
        }

        item.setOrder(this);
        this.getItems().add(item);
    }

    public UUID getCustomerId() {
        if (this.customer == null) {
            return null;
        }
        return this.customer.getId();
    }

    public void addEvents(Collection<Object> events) {

		if (!Objects.isNull(events)) {

			for (Object event : events) {
				this.registerEvent(event);	
			} 
			
		}

	}

	public void clearEvents() {

		super.clearDomainEvents();

	}

}
