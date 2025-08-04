package br.com.transformers.ems.algashop.ordering.domain.model.valueobject;

public record Phone(
        String value
) {

    public Phone(String value) {
        this.value = value;
    }
}
