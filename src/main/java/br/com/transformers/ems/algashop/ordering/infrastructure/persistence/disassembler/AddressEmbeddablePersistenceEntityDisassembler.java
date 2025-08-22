package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import org.springframework.stereotype.Component;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Address;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ZipCode;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.AddressEmbeddable;

@Component
public class AddressEmbeddablePersistenceEntityDisassembler {
    
    public static Address toDomainEntity(AddressEmbeddable embeddable) {

        return Address.builder()
            .city(embeddable.getCity())
            .complement(embeddable.getComplement())
            .neighborhood(embeddable.getNeighborhood())
            .number(embeddable.getNumber())
            .state(embeddable.getState())
            .street(embeddable.getStreet())
            .zipCode(new ZipCode(embeddable.getZipCode()))
        .build();
            
    }

}
