package br.com.transformers.ems.algashop.ordering.model.entity.databuilder;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Product;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ProductName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ProductId;

public class ProductTestDataBuilder {

    private ProductTestDataBuilder() {

    }

    public static Product.ProductBuilder aProduct() {

        return Product.builder()
            .id(new ProductId())
            .name(new ProductName("pn"))
            .value(Money.ZERO)
            .inStock(true)
            ;

    }

    public static Product.ProductBuilder aUnavailableProduct() {

        return Product.builder()
            .id(new ProductId())
            .name(new ProductName("pnUn"))
            .value(Money.ZERO)
            .inStock(false)
            ;

    }

}
