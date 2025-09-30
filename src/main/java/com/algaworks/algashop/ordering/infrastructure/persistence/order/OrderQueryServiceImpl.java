package com.algaworks.algashop.ordering.infrastructure.persistence.order;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.application.order.query.CustomerMinimalOutput;
import com.algaworks.algashop.ordering.application.order.query.OrderDetailOutput;
import com.algaworks.algashop.ordering.application.order.query.OrderQueryService;
import com.algaworks.algashop.ordering.application.order.query.OrderSummaryOutput;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.application.utility.PageFilter;
import com.algaworks.algashop.ordering.domain.model.order.OrderId;
import com.algaworks.algashop.ordering.domain.model.order.OrderNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CompoundSelection;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderPersistenceEntityRepository repository;
    private final Mapper mapper;
    private final EntityManager entityManager;

    @Override
    public OrderDetailOutput findById(String id) {

        var entity = this.repository.findById(new OrderId(id).value().toLong())
                .orElseThrow(() -> new OrderNotFoundException());

        return this.mapper.convert(entity, OrderDetailOutput.class);

    }

    @Override
    public Page<OrderSummaryOutput> filter(PageFilter filter) {

        long totalQueryResults = countTotalQueryResults(filter);

        if (totalQueryResults == 0L) {

            PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize());

            return new PageImpl<>(new ArrayList<>(), pageRequest, totalQueryResults);

        }

        return filterQuery(filter, totalQueryResults);

    }

    private Page<OrderSummaryOutput> filterQuery(PageFilter filter, long totalQueryResults) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderSummaryOutput> query = builder.createQuery(OrderSummaryOutput.class);
        Root<OrderPersistenceEntity> root = query.from(OrderPersistenceEntity.class);

        Path<Object> customer = root.get("customer");

        CompoundSelection<OrderSummaryOutput> compoundSelection = builder.construct(OrderSummaryOutput.class,
                root.get("id"),

                builder.construct(CustomerMinimalOutput.class,
                        customer.get("id"),
                        customer.get("firstName"),
                        customer.get("lastName"),
                        customer.get("email"),
                        customer.get("document"),
                        customer.get("phone")),

                root.get("totalItems"),
                root.get("totalAmount"),
                root.get("placedAt"),
                root.get("paidAt"),
                root.get("canceledAt"),
                root.get("status"),
                root.get("paymentMethod")

        );

        query.select(compoundSelection);

        TypedQuery<OrderSummaryOutput> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(filter.getSize() * filter.getPage());
        typedQuery.setMaxResults(filter.getSize());

        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize());;

        return new PageImpl<>(typedQuery.getResultList(), pageRequest, totalQueryResults);

    }

    private long countTotalQueryResults(PageFilter filter) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<OrderPersistenceEntity> root = query.from(OrderPersistenceEntity.class);
        Expression<Long> selection = builder.count(root);
        query.select(selection);

        TypedQuery<Long> counted = entityManager.createQuery(query);

        return counted.getSingleResult();
    }

}
