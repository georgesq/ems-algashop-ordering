package com.algaworks.algashop.ordering.application.customer.management;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.algaworks.algashop.ordering.application.commons.AddressData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOutput {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String document;
    private LocalDate birthDate;    
    private Boolean promotionNotificationsAllowed;
    private Integer loyaltyPoints;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;

    private AddressData address;
    

}
