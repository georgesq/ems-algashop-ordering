package com.algaworks.algashop.ordering.application.shoppingcart.management;

import java.util.UUID;

import com.algaworks.algashop.ordering.application.checkout.BillingData;
import com.algaworks.algashop.ordering.application.checkout.ShippingInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutInput {

    private UUID shoppingCartId;
    private String paymentMethod;
    private ShippingInput shipping;
    private BillingData billing;
    
}