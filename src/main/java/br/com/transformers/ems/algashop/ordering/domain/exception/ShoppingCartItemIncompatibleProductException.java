package br.com.transformers.ems.algashop.ordering.domain.exception;

import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.ProductId;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.ShoppingCartId;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.ShoppingCartItemId;

public class ShoppingCartItemIncompatibleProductException extends DomainException{

    public ShoppingCartItemIncompatibleProductException(ShoppingCartId shoppingCartId, ShoppingCartItemId id, ProductId productId) {
        super("Error: Shopping cart item with ID " + id + " in shopping cart " + shoppingCartId + " is incompatible with product " + productId);
    }

}
