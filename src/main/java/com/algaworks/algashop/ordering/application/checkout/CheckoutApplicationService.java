package com.algaworks.algashop.ordering.application.checkout;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algashop.ordering.domain.model.commons.ZipCode;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.customer.Customers;
import com.algaworks.algashop.ordering.domain.model.order.Billing;
import com.algaworks.algashop.ordering.domain.model.order.CheckoutService;
import com.algaworks.algashop.ordering.domain.model.order.Orders;
import com.algaworks.algashop.ordering.domain.model.order.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.order.Shipping;
import com.algaworks.algashop.ordering.domain.model.order.shipping.OriginAddressService;
import com.algaworks.algashop.ordering.domain.model.order.shipping.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.order.shipping.ShippingCostService.CalculationResult;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartId;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartNotFoundException;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCarts;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckoutApplicationService {

    private final Customers customers;
    private final CheckoutService checkoutService; 
    private final Orders orders;
    private final ShoppingCarts shoppingCarts;
    private final ShippingCostService shippingCostService;
    private final OriginAddressService originAddressService;
    private final BillingInputDisassembler billingInputDisassembler;
    private final ShippingInputDisassembler shippingInputDisassembler;

    @Transactional
    public String checkout(CheckoutInput input) {

        // valid
        Objects.requireNonNull(input);

        // arrange
        ShoppingCart shoppingCart = shoppingCarts.ofId(new ShoppingCartId(input.getShoppingCartId()))
            .orElseThrow(() -> new ShoppingCartNotFoundException());
        var customer = this.customers.ofId(shoppingCart.customerId()).orElseThrow(() -> new CustomerNotFoundException(shoppingCart.customerId()));
        Billing billing = billingInputDisassembler.toDomainModel(input.getBilling());
        CalculationResult shippingCalculationResult = this.shippingCostService.calculate(ShippingCostService.CalculationRequest
            .builder()
            .origin(this.originAddressService.originAddress().zipCode())
            .destination(new ZipCode(input.getShipping().getAddress().getZipCode()))
            .build()
        );
        Shipping shipping = shippingInputDisassembler.toDomainModel(input.getShipping(), shippingCalculationResult);
        PaymentMethod paymentMethod = PaymentMethod.valueOf(input.getPaymentMethod());

        // act
        var order = this.checkoutService.checkout(customer, shoppingCart, billing, shipping, paymentMethod);

        this.orders.add(order);

        return order.id().toString();

    }

}