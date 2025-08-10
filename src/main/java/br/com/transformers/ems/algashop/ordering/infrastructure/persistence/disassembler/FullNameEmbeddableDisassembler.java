package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.disassembler;

import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.FullName;

public class FullNameEmbeddableDisassembler {

    public static FullName toDomainEntity(String value) {
        return new FullName(value.split(" ")[0], value.split(" ")[1]);
    }

}
