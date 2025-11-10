package com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.application.shoppingcart.query.ShoppingCartOutput;
import com.algaworks.algashop.ordering.application.shoppingcart.query.ShoppingCartQueryService;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShoppingCartQueryServiceImpl implements ShoppingCartQueryService {

    private final ShoppingCartPersistenceEntityRepository repository;
    
    private final Mapper mapper;

    @Override
    public ShoppingCartOutput findById(UUID shoppingCartId) {

        var entity = this.repository.findById(shoppingCartId).orElseThrow(() -> new ShoppingCartNotFoundException());

        return mapper.convert(entity, ShoppingCartOutput.class);
        
    }

    @Override
    public ShoppingCartOutput findByCustomerId(UUID customerId) {

        var entity = this.repository.findByCustomer_Id(customerId)
            .orElseThrow(() -> new ShoppingCartNotFoundException());
            
        return mapper.convert(entity, ShoppingCartOutput.class);
        
    }
    
}
