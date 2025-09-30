package com.algaworks.algashop.ordering.application.order.query;

import org.springframework.data.domain.Page;

import com.algaworks.algashop.ordering.application.utility.PageFilter;

public interface OrderQueryService {
    
    OrderDetailOutput findById(String id);

    Page<OrderSummaryOutput> filter(PageFilter filter);

}
