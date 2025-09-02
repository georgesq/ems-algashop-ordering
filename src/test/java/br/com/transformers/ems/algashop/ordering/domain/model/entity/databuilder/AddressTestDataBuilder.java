package br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Address;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Product;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ProductName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ZipCode;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ProductId;

public class AddressTestDataBuilder {

    private AddressTestDataBuilder() {

    }

    public static Address.AddressBuilder anAddress() {

        return Address.builder()
            .city("ssa" )
            .complement("ap 1001")
            .neighborhood("boca do rio")
            .number(178)
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
