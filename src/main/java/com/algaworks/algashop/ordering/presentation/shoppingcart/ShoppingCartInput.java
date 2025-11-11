package com.algaworks.algashop.ordering.presentation.shoppingcart;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShoppingCartInput {
	@NotNull
	private UUID customerId;
}