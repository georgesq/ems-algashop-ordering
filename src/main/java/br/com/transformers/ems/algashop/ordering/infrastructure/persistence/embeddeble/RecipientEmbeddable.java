package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.embeddeble;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RecipientEmbeddable {
    
    private String firstName;
    private String lastName;
    private String document;
    private String phone;
    

}
