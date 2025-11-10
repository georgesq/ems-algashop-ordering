package com.algaworks.algashop.ordering.infrastructure.product.client.http;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ProductCatalogAPIConfig {
    
    @Bean
    public ProductCatalogAPIClient productCatalogAPIClient(RestClient.Builder builder,
            @Value("${algashop.integrations.product-catalog.url}") String url) {

        Objects.requireNonNull(url);

        var restClient = builder
                        .baseUrl(url)
                        .build();
        var restClientAdapter = RestClientAdapter.create(restClient);
        var proxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();

        return proxyFactory.createClient(ProductCatalogAPIClient.class);

    }
}
