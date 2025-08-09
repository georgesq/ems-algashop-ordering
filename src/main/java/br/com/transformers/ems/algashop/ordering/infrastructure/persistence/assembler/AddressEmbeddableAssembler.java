package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Address;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble.AddressEmbeddable;

public class AddressEmbeddableAssembler {

    public static AddressEmbeddable fromDomain(Address address) {

        return merge(AddressEmbeddable.builder().build(), address);
        
    }

    private static AddressEmbeddable merge(AddressEmbeddable addressEmbeddable, Address address) {

        addressEmbeddable.setStreet(address.street().toString());
        addressEmbeddable.setNumber(address.number());
        addressEmbeddable.setComplement(address.complement().toString());
        addressEmbeddable.setNeighborhood(address.neighborhood().toString());
        addressEmbeddable.setCity(address.city().toString());
        addressEmbeddable.setState(address.state().toString());
        addressEmbeddable.setZipCode(address.zipCode().toString());

        return addressEmbeddable;
    }

}
