package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import org.springframework.stereotype.Component;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Billing;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.BillingEmbeddable;

@Component
public class BillingEmbeddableAssembler {

    private BillingEmbeddableAssembler() {
        // Private constructor to prevent instantiation
    }

    public static BillingEmbeddable fromDomain(Billing domain) {

        return merge(BillingEmbeddable.builder().build(), domain);
        
    }

    public static BillingEmbeddable merge(BillingEmbeddable embeddable, Billing domain) {

        embeddable.setAddress(AddressEmbeddableAssembler.fromDomain(domain.address()));
        embeddable.setDocument(domain.document().toString());
        embeddable.setFirstName(domain.fullName().firstName());
        embeddable.setLastName(domain.fullName().lastName());
        embeddable.setPhone(domain.phone().toString());
        embeddable.setEmail(domain.email().toString());
        
        return embeddable;

    }
}
