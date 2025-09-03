package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.Address;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.model.valueobject.ZipCode;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;

public class AddressTestDataBuilder {

    private AddressTestDataBuilder() {

    }

    public static Address.AddressBuilder anAddress() {

        return Address.builder()
            .city("ssa" )
            .complement("ap 1001")
            .neighborhood("boca do rio")
            .number("178")
            .state("BA")
            .street("r. bea")
            .zipCode(new ZipCode("41710790"))
            ;

    }

    public static Product.ProductBuilder aUnavailableProduct() {

        return Product.builder()
            .id(new ProductId())
            .name(new ProductName("pnUn"))
            .price(Money.ZERO)
            .inStock(false)
            ;

    }

}
