package com.algaworks.algashop.ordering.infrastructure.product.client.http;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.product.ProductCatalogService;
import com.algaworks.algashop.ordering.domain.model.product.ProductId;
import com.algaworks.algashop.ordering.domain.model.product.ProductName;
import com.algaworks.algashop.ordering.presentation.BadGatewayException;
import com.algaworks.algashop.ordering.presentation.GatewayTimeoutException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductCatalogServiceHttpImpl implements ProductCatalogService {

    private final ProductCatalogAPIClient productCatalogAPIClient;

    @Override
    public Optional<Product> ofId(ProductId productId) {
        ProductResponse response = null;

        try {

            response = this.productCatalogAPIClient.getById(productId.value());
            
        } catch (ResourceAccessException e) {
            
            throw new GatewayTimeoutException("Gateway Timeout when accessing Product Catalog Service");

        } catch (HttpClientErrorException e) {

            throw new BadGatewayException("Bad Gateway when accessing Product Catalog Service", e);

        } 

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
