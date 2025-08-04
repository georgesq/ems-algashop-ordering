package br.com.transformers.ems.algashop.ordering.domain.model.exception;

public class LoyaltyPointException extends DomainException {

    public LoyaltyPointException(Integer value) {
        super(String.format("Loyalty point value invalid %d", value));
    }

}
