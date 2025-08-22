package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.AddressEmbeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
public class CustomerPersistenceEntity {

    @Id
    private UUID id;

    private String fullName;

    private LocalDate birthDate;

    private String email;

    private String phone;

    private String document;

    private Boolean promotionNotificaficationsAllowed;

    private Boolean archived;

    private Integer loyaltyPoints;

    private OffsetDateTime registeredAt;

    private OffsetDateTime archivedAt;

    @Embedded
    private AddressEmbeddable address;

    @CreatedBy
    private UUID createdByUserId;

    @LastModifiedBy
    private UUID lastModifiedByUserId;

    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;

    @Version
    private Long version;

    @Builder
    public CustomerPersistenceEntity(UUID id, String fullName, LocalDate birthDate, String email, String phone,
            String document, Boolean promotionNotificaficationsAllowed, Boolean archived, Integer loyaltyPoints,
            OffsetDateTime registeredAt, OffsetDateTime archivedAt, AddressEmbeddable address, UUID createdByUserId,
            UUID lastModifiedByUserId, OffsetDateTime lastModifiedAt, Long version) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.document = document;
        this.promotionNotificaficationsAllowed = promotionNotificaficationsAllowed;
        this.archived = archived;
        this.loyaltyPoints = loyaltyPoints;
        this.registeredAt = registeredAt;
        this.archivedAt = archivedAt;
        this.address = address;
        this.createdByUserId = createdByUserId;
        this.lastModifiedByUserId = lastModifiedByUserId;
        this.lastModifiedAt = lastModifiedAt;
        this.version = version;
    }


}