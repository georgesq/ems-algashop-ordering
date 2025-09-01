package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.BillingEmbeddable;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.ShippingEmbeddable;
import jakarta.persistence.CascadeType;
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
@Table(name = "\"order\"")
@Getter
@Setter
@NoArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
public class OrderPersistenceEntity {
    @Id
    private Long id;

    @JoinColumn
    @ManyToOne(optional = false)
    private CustomerPersistenceEntity customer;

    private BigDecimal totalAmount;
    private Long totalItems;
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
    private BillingEmbeddable billing;

    @Embedded
    private ShippingEmbeddable shipping;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItemPersistenceEntity> items = new HashSet<>();

    @Builder
    public OrderPersistenceEntity(Long id, CustomerPersistenceEntity customer, BigDecimal totalAmount, Long totalItems, String status, String paymentMethod, OffsetDateTime placedAt, OffsetDateTime paidAt, OffsetDateTime canceledAt, OffsetDateTime readyAt, UUID createdByUserId, OffsetDateTime lastModifiedAt, UUID lastModifiedByUserId, Long version, BillingEmbeddable billing, ShippingEmbeddable shipping, Set<OrderItemPersistenceEntity> items) {
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
}