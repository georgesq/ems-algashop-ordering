package com.algaworks.algashop.ordering.infrastructure.fake;

import org.springframework.stereotype.Component;

import com.algaworks.algashop.ordering.domain.model.service.OriginAddressService;
import com.algaworks.algashop.ordering.domain.model.valueobject.Address;
import com.algaworks.algashop.ordering.domain.model.valueobject.ZipCode;

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
