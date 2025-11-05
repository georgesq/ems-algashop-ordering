package com.algaworks.algashop.ordering.infrastructure.product.client.http;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.product.ProductCatalogService;
import com.algaworks.algashop.ordering.domain.model.product.ProductId;
import com.algaworks.algashop.ordering.domain.model.product.ProductName;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductCatalogServiceHttpImpl implements ProductCatalogService {

    private final ProductCatalogAPIClient productCatalogAPIClient;

    @Override
    public Optional<Product> ofId(ProductId productId) {

        var response = this.productCatalogAPIClient.getById(productId.value());

        if (response == null) {

            return Optional.empty();

        }

        return Optional.of(new Product(
            new ProductId(response.getId()),
            new ProductName(response.getName()),
            new Money(response.getSalePrice()),
            response.getInStock()
        ));

    };

}
