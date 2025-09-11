package com.algaworks.algashop.ordering.application.checkout.management;

import org.springframework.stereotype.Component;

import com.algaworks.algashop.ordering.application.checkout.ShippingInput;
import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Address;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Document;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.Phone;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.ZipCode;
import com.algaworks.algashop.ordering.domain.model.order.shipping.service.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.order.shipping.valueobject.Recipient;
import com.algaworks.algashop.ordering.domain.model.order.shipping.valueobject.Shipping;

@Component
class ShippingInputDisassembler {

    public Shipping toDomainModel(ShippingInput shippingInput, 
				    ShippingCostService.CalculationResult shippingCalculationResult) {
        AddressData address = shippingInput.getAddress();
        return Shipping.builder()
                .cost(shippingCalculationResult.cost())
                .expectedDate(shippingCalculationResult.expectedDate())
                .recipient(Recipient.builder()
                        .fullName(new FullName(
                                shippingInput.getRecipient().getFirstName(),
                                shippingInput.getRecipient().getLastName()))
                        .document(new Document(shippingInput.getRecipient().getDocument()))
                        .phone(new Phone(shippingInput.getRecipient().getPhone()))
                        .build())
                .address(Address.builder()
                        .street(address.getStreet())
                        .number(address.getNumber())
                        .complement(address.getComplement())
                        .neighborhood(address.getNeighborhood())
                        .city(address.getCity())
                        .state(address.getState())
                        .zipCode(new ZipCode(address.getZipCode()))
                        .build())
                .build();
    }
}