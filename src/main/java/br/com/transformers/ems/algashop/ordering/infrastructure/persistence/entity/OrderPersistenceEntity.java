package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Builder
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
public class OrderPersistenceEntity {

    @Id
    private Long id;

    private UUID customerId;

    private BigDecimal totalAmount;
    private Integer totalItems;
    
    private OffsetDateTime createdAt;
    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime readAt;
    
    // private Billing billing;
    // private Shipping shipping;
    
    private String status;
    private String paymentMethod;
    
    // private Set<OrderItem> items;

    @CreatedBy
    private UUID createdByUser;
    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;
    @LastModifiedBy
    private UUID lastModifiedByUser;

    @Version
    private Long version;

}