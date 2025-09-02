package br.com.transformers.ems.algashop.ordering.domain.service;

import java.util.Optional;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Product;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ProductId;

public interface ProductCatalogService {
    
    Optional<Product> ofId(ProductId productId);

}
