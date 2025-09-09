package com.algaworks.algashop.ordering.domain.model.order.entity;

import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Address;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.ZipCode;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductId;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductName;

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
