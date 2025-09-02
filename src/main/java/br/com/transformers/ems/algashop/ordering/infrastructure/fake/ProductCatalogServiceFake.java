package br.com.transformers.ems.algashop.ordering.infrastructure.fake;

import java.util.Optional;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Money;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Product;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.ProductName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ProductId;
import br.com.transformers.ems.algashop.ordering.domain.service.ProductCatalogService;

public class ProductCatalogServiceFake implements ProductCatalogService {

    @Override
    public Optional<Product> ofId(ProductId productId) {

        return Optional.of(Product.builder()
            .id(productId)
            .name(new ProductName("Fake Product"))
            .price(new Money( "100.0"))
            .inStock(true)
        .build());

    }
    
}
