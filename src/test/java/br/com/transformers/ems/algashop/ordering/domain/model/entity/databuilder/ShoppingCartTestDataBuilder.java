package br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.ShoppingCart;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;

public class ShoppingCartTestDataBuilder {
    
    boolean withItems = true;

    private ShoppingCartTestDataBuilder() {

    }

    public static ShoppingCartTestDataBuilder aShoppingCart() {

        return new ShoppingCartTestDataBuilder();

    } 

    public ShoppingCartTestDataBuilder withItems(boolean withItems) {
        this.withItems = withItems;

        return this;
    }

    public ShoppingCart build() {
        var domain = ShoppingCart.draft(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID);

        if (withItems) {
            domain.addItem(
                ProductTestDataBuilder.aProduct().build(), 
                Quantity.DEZ
            );
            domain.addItem(
                ProductTestDataBuilder.aProduct().build(), 
                new Quantity(2L)
            );
        }

        return domain;
    }


}
