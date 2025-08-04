package br.com.transformers.ems.algashop.ordering.domain.model.exception;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ProductId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;

public class ShoppingCartItemIncompatibleProductException extends DomainException{

    public ShoppingCartItemIncompatibleProductException(ShoppingCartId shoppingCartId, ShoppingCartItemId shoppingCartItemId,
            ProductId productId) {
        super(String.format("The product with id %s is incompatible with the shopping cart item with id %s in shopping cart %s.",
                productId, shoppingCartItemId, shoppingCartId));
    }   
    
}
