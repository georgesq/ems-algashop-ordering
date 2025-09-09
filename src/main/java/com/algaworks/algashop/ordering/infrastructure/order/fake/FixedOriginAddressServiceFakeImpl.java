package com.algaworks.algashop.ordering.infrastructure.order.fake;

import org.springframework.stereotype.Component;

import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Address;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.ZipCode;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.service.OriginAddressService;

@Component
public class FixedOriginAddressServiceFakeImpl implements OriginAddressService {

    @Override
    public Address originAddress() {
        return Address.builder()
                .street("Rua das Flores")
                .number("123")
                .complement("Apto 45")
                .neighborhood("Jardim Primavera")
                .city("São Paulo")
                .state("SP")
                .zipCode(new ZipCode("01234"))
                .build();
    }
    
}
