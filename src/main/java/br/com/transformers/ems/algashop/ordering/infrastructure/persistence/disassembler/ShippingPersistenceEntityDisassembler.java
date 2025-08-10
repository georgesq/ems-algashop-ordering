package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import org.springframework.stereotype.Component;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Shipping;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.ShippingEmbeddable;

@Component
public class ShippingPersistenceEntityDisassembler {
    
    public static Shipping toDomainEntity(ShippingEmbeddable embeddable) {

        return Shipping.builder()
            .address(AddressPersistenceEntityDisassembler.toDomainEntity(embeddable.getAddress()))
            .cost(new Money(embeddable.getCost()))
            .expectedDate(embeddable.getExpectedDate())
            .recipient(RecipientPersistenceEntityDisassembler.toDomainEntity(embeddable.getRecipient()))
        .build();
            
    }

}
