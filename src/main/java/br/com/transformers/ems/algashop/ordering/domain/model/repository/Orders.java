package br.com.transformers.ems.algashop.ordering.domain.model.repository;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.Order;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.OrderId;

public interface Orders extends Repository<Order, OrderId> {
    
}
