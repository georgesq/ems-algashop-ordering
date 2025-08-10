package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "\"order_item\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class OrderItemPersistenceEntity {

    @Id
    private Long id;
    private Long orderId;

    private UUID productId;
    private String productName;

    private BigDecimal price;
    private Long quantity;

    private BigDecimal totalAmmount;

    @JoinColumn
    @ManyToOne
    private OrderPersistenceEntity order;

    public Long getOrderId() {
        if (Objects.isNull(this.getOrder())) {
            return null;
        }

        return this.getOrder().getId(); 
    }

}
