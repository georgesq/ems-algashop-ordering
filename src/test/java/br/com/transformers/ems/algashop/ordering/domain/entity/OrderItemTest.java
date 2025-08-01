package br.com.transformers.ems.algashop.ordering.domain.entity;

import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.entity.databuilder.ProductTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.Quantity;
import br.com.transformers.ems.algashop.ordering.domain.valueobject.id.OrderId;

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
