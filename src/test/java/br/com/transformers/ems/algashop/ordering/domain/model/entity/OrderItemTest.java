package br.com.transformers.ems.algashop.ordering.domain.model.entity;

import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderItem;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.ProductTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;

public class OrderItemTest {

    @Test
    void testCreateDraft() {

        OrderItem.brandNew(
            new OrderId(),
            ProductTestDataBuilder.aProduct().build(),
            Quantity.ZERO
        );

    }
}
