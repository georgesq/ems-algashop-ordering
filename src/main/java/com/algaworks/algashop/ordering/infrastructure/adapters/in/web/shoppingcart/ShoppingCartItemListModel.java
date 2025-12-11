package com.algaworks.algashop.ordering.infrastructure.adapters.in.web.shoppingcart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ShoppingCartItemOutput;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemListModel {
    private List<ShoppingCartItemOutput> items = new ArrayList<>();
}
