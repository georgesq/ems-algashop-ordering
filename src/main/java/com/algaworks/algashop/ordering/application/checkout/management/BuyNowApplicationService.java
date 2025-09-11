package com.algaworks.algashop.ordering.application.checkout.management;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.algaworks.algashop.ordering.application.checkout.BuyNowInput;
import com.algaworks.algashop.ordering.application.checkout.ShippingInput;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.ZipCode;
import com.algaworks.algashop.ordering.domain.model.customer.valueobject.CustomerId;
import com.algaworks.algashop.ordering.domain.model.order.entity.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.order.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.order.service.BuyNowService;
import com.algaworks.algashop.ordering.domain.model.order.shipping.service.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.order.shipping.service.ShippingCostService.CalculationRequest;
import com.algaworks.algashop.ordering.domain.model.order.shipping.service.ShippingCostService.CalculationResult;
import com.algaworks.algashop.ordering.domain.model.product.exception.ProductNotFoundException;
import com.algaworks.algashop.ordering.domain.model.product.service.ProductCatalogService;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductId;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.service.OriginAddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuyNowApplicationService {
    
    private final BuyNowService buyNowService;
    private final ShippingCostService costService;
    private final ProductCatalogService productCatalogService;
    private final OriginAddressService originAddressService;

    private final Orders orders;

    private final ShippingInputDisassembler shippingInputDisassembler;
    private final BillingInputDisassembler billingInputDisassembler;


    public String buyNow(BuyNowInput input) {

        Objects.requireNonNull(input);

        var paymentMethod = PaymentMethod.valueOf(input.getPaymentMethod());
        var customerId = new CustomerId(input.getCustomerId());
        var quantity = new Quantity(input.getQuantity());

        var product = findProduct(input);
        
        var shippingCalculationResult = this.calculateShippingCost(input.getShipping());
        
        var shipping = shippingInputDisassembler.toDomainModel(input.getShipping(), shippingCalculationResult);
        var billing = billingInputDisassembler.toDomainModel(input.getBilling());

        var order = this.buyNowService.buyNow(
            product, 
            customerId, 
            billing, 
            shipping, 
            quantity, 
            paymentMethod
        );

        this.orders.add(order);

        return order.id().toString();

    }


    private CalculationResult calculateShippingCost(ShippingInput shipping) {

        var origin = this.originAddressService.originAddress().zipCode();
        var destination = new ZipCode(shipping.getAddress().getZipCode());

        var calculationRequest = CalculationRequest.builder()
            .origin(origin)
            .destination(destination)
            .build();

        return this.costService.calculate(calculationRequest);

    }

    private Product findProduct(BuyNowInput input) {

        return this.productCatalogService.ofId(new ProductId(input.getProductId()))
            .orElseThrow(() -> new ProductNotFoundException(input.getProductId()));

    }
}
