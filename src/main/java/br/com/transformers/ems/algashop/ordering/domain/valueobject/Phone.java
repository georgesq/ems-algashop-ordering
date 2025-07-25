package br.com.transformers.ems.algashop.ordering.domain.valueobject;

public record Phone(
        String value
) {

    public Phone(String value) {
        this.value = value;
    }
}
