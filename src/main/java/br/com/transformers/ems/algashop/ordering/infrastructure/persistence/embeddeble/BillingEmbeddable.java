package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BillingEmbeddable {

        private String fullName;
        private String document;
        private String phone;
        @Embedded
        private AddressEmbeddable address;
        private String email;
    
}
