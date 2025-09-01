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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(of = "id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Table(name = "\"customer\"")
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CustomerPersistenceEntity {

    @Id
	@EqualsAndHashCode.Include
	private UUID id;
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private String email;
	private String phone;
	private String document;
	private Boolean promotionNotificationsAllowed;
	private Boolean archived;
	private OffsetDateTime registeredAt;
	private OffsetDateTime archivedAt;
	@Embedded
	private AddressEmbeddable address;
	private Integer loyaltyPoints;
	
  	@Version
	private Long version;

	@CreatedBy
	private UUID createdByUserId;

	@LastModifiedDate
	private OffsetDateTime lastModifiedAt;

	@LastModifiedBy
	private UUID lastModifiedByUserId;
}