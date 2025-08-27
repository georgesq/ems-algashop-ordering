package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
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
    
    private OffsetDateTime createdAt;
    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime readAt;
    
    @Embedded
    private BillingEmbeddable billing;
    @Embedded
    private ShippingEmbeddable shipping;
    
    private String status;
    private String paymentMethod;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItemPersistenceEntity> items = new HashSet<>();

    @CreatedBy
    private UUID createdByUser;
    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;
    @LastModifiedBy
    private UUID lastModifiedByUser;

    @Version
    private Long version;

    @Builder
    public OrderPersistenceEntity(Long id, CustomerPersistenceEntity customer, BigDecimal totalAmount, Long totalItems,
            OffsetDateTime createdAt, OffsetDateTime placedAt, OffsetDateTime paidAt, OffsetDateTime canceledAt,
            OffsetDateTime readAt, BillingEmbeddable billing, ShippingEmbeddable shipping, String status,
            String paymentMethod, Set<OrderItemPersistenceEntity> items, UUID createdByUser,
            OffsetDateTime lastModifiedAt, UUID lastModifiedByUser, Long version) {
        this.setId(id);
        this.setCustomer(customer);
        this.setTotalAmount(totalAmount);
        this.setTotalItems(totalItems);
        this.setCreatedAt(createdAt);
        this.setPlacedAt(placedAt);
        this.setPaidAt(paidAt);
        this.setCanceledAt(canceledAt);
        this.setReadAt(readAt);
        this.setBilling(billing);
        this.setShipping(shipping);
        this.setStatus(status);
        this.setPaymentMethod(paymentMethod);
        this.replaceItems(items);
        this.setCreatedByUser(createdByUser);
        this.setLastModifiedAt(lastModifiedAt);
        this.setLastModifiedByUser(lastModifiedByUser);
        this.setVersion(version);
    }

    public void replaceItems(Set<OrderItemPersistenceEntity> items) {

        if (items == null || items.isEmpty()) {
            this.setItems(new HashSet<>());

            return;
        }

        items.forEach(i-> i.setOrder(this));

        this.setItems(items);

    }

    public void addItem(OrderItemPersistenceEntity item) {

        if (Objects.isNull(item)) {
            return;
        }

        if (items == null || items.isEmpty()) {
            this.setItems(new HashSet<>());
            return;
        }
        
        item.setOrder(this);

        this.getItems().add(item);

    }

    public UUID getCustomerId() {

        if (!Objects.isNull(this.customer)) {
            return this.customer.getId();
        }

        return null;
    }

}