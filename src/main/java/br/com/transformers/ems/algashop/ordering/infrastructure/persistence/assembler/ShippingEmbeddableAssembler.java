package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import org.springframework.stereotype.Component;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Shipping;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.ShippingEmbeddable;

@Component
public class ShippingEmbeddableAssembler {
    
    private ShippingEmbeddableAssembler() {
        // Private constructor to prevent instantiation
    }

    public static ShippingEmbeddable fromDomain(Shipping domain) {

        return merge(ShippingEmbeddable.builder().build(), domain);

    }

    public static ShippingEmbeddable merge(ShippingEmbeddable embeddable, Shipping domain) {

        embeddable.setCost(domain.cost().value());
        embeddable.setExpectedDate(domain.expectedDate());
        embeddable.setRecipient(RecipientEmbeddableAssembler.fromDomain(domain.recipient()));
        embeddable.setAddress(AddressEmbeddableAssembler.fromDomain(domain.address()));

        return embeddable;

    }
}
