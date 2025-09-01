package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
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
@Table(name = "\"shoppingcart\"")
@Getter
@Setter
@NoArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
public class ShoppingCartPersistenceEntity {
    
    @Id
    private UUID id;

    private BigDecimal totalAmount;
    private Long totalItems;

    @JoinColumn
    @ManyToOne(optional = false)
    private CustomerPersistenceEntity customer;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    private Set<ShoppingCartItemPersistenceEntity> items = new HashSet<>();

    @CreatedBy
    private UUID createdByUser;
    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;
    @LastModifiedBy
    private UUID lastModifiedByUser;
    @CreatedDate
    private OffsetDateTime createdAt;

    @Version
    private Long version;

    @Builder
    public ShoppingCartPersistenceEntity(UUID id, CustomerPersistenceEntity customer, BigDecimal totalAmount, Long totalItems,
            Set<ShoppingCartItemPersistenceEntity> items, UUID createdByUser, OffsetDateTime lastModifiedAt,
            UUID lastModifiedByUser, OffsetDateTime createdAt, Long version) {
        this.setId(id);
        this.setCustomer(customer);
        this.setTotalAmount(totalAmount);
        this.setTotalItems(totalItems);
        this.setCreatedByUser(createdByUser);
        this.setLastModifiedAt(lastModifiedAt);
        this.setLastModifiedByUser(lastModifiedByUser);
        this.setCreatedAt(createdAt);
        this.setVersion(version);

        this.replaceItems(items);
    }

    public void replaceItems(Set<ShoppingCartItemPersistenceEntity> items) {

        if (items == null || items.isEmpty()) {
            this.setItems(new HashSet<>());

            return;
        }

        items.forEach(i-> i.setShoppingCart(this));

        this.setItems(items);

    }

    public void addItem(ShoppingCartItemPersistenceEntity item) {

        if (Objects.isNull(item)) {
            return;
        }

        if (items == null || items.isEmpty()) {
            this.setItems(new HashSet<>());
            return;
        }
        
        item.setShoppingCart(this);

        this.items.add(item);

    }

    public UUID getCustomerId() {

        if (!Objects.isNull(this.customer)) {
            return this.customer.getId();
        }

        return null;
        
    }
    
}
