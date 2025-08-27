package br.com.transformers.ems.algashop.ordering.domain.model.repository;

import java.time.Year;
import java.util.List;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;

public interface Orders extends Repository<Order, OrderId> {
    
    List<Order> placedByCustomerInYear(CustomerId customerId, Year year);

}
