package br.com.transformers.ems.algashop.ordering.domain.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.OrderStatus;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.ProductTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Quantity;

public class CustomerLoyaltyPointsServiceTest {

    CustomerLoyaltyPointsService service = new CustomerLoyaltyPointsService();

    @Test
    void givenValidCustomerAndOrder_whenAddingPoints_thenPointsAreAdded() {

        // Arrange
        var customer = CustomerTestDataBuilder.existingCustomer().build();
        var order = OrderTestDataBuilder.anOrder().status(OrderStatus.READY).build();

        // Act
        service.addPoints(customer, order);

        // Assert
        Assertions.assertThat(customer.loyaltyPoints().value()).isEqualTo(45);
        
    }

    @Test
    void givenValidCustomerAndOrderWithLowTotalAmount_whenAddingPoints_thenNotAccumulate() {

        // Arrange
        var customer = CustomerTestDataBuilder.existingCustomer().build();
        var order = OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).withItems(false).build();
        
        order.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(1l));
        order.place();
        order.markAsPaid();
        order.markAsReady();

        // Act
        service.addPoints(customer, order);

        // Assert
        Assertions.assertThat(customer.loyaltyPoints().value()).isEqualTo(0);
        
    }

}
