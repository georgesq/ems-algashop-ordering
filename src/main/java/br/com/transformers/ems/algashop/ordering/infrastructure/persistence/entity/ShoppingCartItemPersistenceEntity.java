package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

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
@Table(name = "shoppingcart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class ShoppingCartItemPersistenceEntity {

    @Id
    private UUID id;

    private UUID productId;
    private String productName;

    private BigDecimal price;
    private Long quantity;

    private BigDecimal totalAmount;

    private Boolean available;

    @JoinColumn(name = "shoppingcart_id")
    @ManyToOne(optional = false)
    private ShoppingCartPersistenceEntity shoppingCart;
    
    public UUID getShoppingCartId() {
        
        if (Objects.isNull(this.getShoppingCart())) {
            return null;
        }

        return this.getShoppingCart().getId(); 

    }
}
