package br.com.transformers.ems.algashop.ordering.infrastructure.persistence.assembler;

import java.util.UUID;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.transformers.ems.algashop.ordering.domain.model.entity.ShoppingCart;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.ShoppingCartItem;
import br.com.transformers.ems.algashop.ordering.domain.model.entity.databuilder.ShoppingCartTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder.CustomerPersistenceEntityTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder.ShoppingCartItemPersistenceTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.entity.databuilder.ShoppingCartPersistenceEntityTestDataBuilder;
import br.com.transformers.ems.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;

@ExtendWith(MockitoExtension.class)
class ShoppingCartPersistenceEntityAssemblerTest {

    @Mock
    private CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    @InjectMocks
    private ShoppingCartPersistenceEntityAssembler assembler;

    @BeforeEach
    void setup() {

        Mockito.when(customerPersistenceEntityRepository.getReferenceById(Mockito.any(UUID.class)))
                .then(a -> {

                    UUID id = a.getArgument(0, UUID.class);

                    return CustomerPersistenceEntityTestDataBuilder.aCustomer().id(id).build();

                });
    }

    @Test
    void fromDomain_shouldMapDomainToPersistenceEntity() {
        // Arrange
        ShoppingCart domain = ShoppingCartTestDataBuilder.aShoppingCart().withItems(true).build();

        // Act
        ShoppingCartPersistenceEntity entity = assembler.fromDomain(domain);

        // Assert
        Assertions.assertThat(entity.getId()).isEqualTo(domain.id().value());
        Assertions.assertThat(entity.getCreatedAt()).isEqualTo(domain.createdAt());
        Assertions.assertThat(entity.getCustomer().getId()).isSameAs(domain.customerId().value());
        Assertions.assertThat(entity.getItems()).hasSize(domain.items().size());
        for (ShoppingCartItemPersistenceEntity itemEntity : entity.getItems()) {
            ShoppingCartItem domainItem = domain.items().stream()
                    .filter(i -> i.id().value() == itemEntity.getId())
                    .findFirst().orElse(null);
            Assertions.assertThat(domainItem).isNotNull();
            Assertions.assertThat(itemEntity.getProductId()).isEqualTo(domainItem.productId().value());
            Assertions.assertThat(itemEntity.getProductName()).isEqualTo(domainItem.productName().toString());
            Assertions.assertThat(itemEntity.getQuantity()).isEqualTo(domainItem.quantity().value());
            Assertions.assertThat(itemEntity.getPrice()).isEqualTo(domainItem.price().value());
            Assertions.assertThat(itemEntity.getTotalAmount()).isEqualTo(domainItem.totalAmount().value());
            Assertions.assertThat(itemEntity.getAvailable()).isEqualTo(domainItem.available());
        }
    }

    @Test
    public void givenShoppinCartWithNoItems_thenRemoveAllPersistenceEntityItems() {

        ShoppingCart domain = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();
        ShoppingCartPersistenceEntity scpe = ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart().build();

        scpe.addItem(ShoppingCartItemPersistenceTestDataBuilder.aShoppingCartItem(scpe));

        Assertions.assertThat(domain.items()).isEmpty();
        Assertions.assertThat(scpe.getItems()).isNotEmpty();

        assembler.merge(scpe, domain);

        Assertions.assertThat(scpe.getItems()).isEmpty();

    }

    @Test
    public void givenOrderWithItems_thenAddToPersistenceEntity() {

        ShoppingCart domain = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();
        ShoppingCartPersistenceEntity scpe = ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart().build();

        Assertions.assertThat(domain.items()).isEmpty();
        Assertions.assertThat(scpe.getItems()).isNotEmpty();

        assembler.merge(scpe, domain);

        Assertions.assertThat(scpe.getItems().size()).isEqualTo(domain.items().size());

    }

    @Test
    public void givenOrderWithItems_whenMerge_thenRemoveCorretly() {

        ShoppingCart domain = ShoppingCartTestDataBuilder.aShoppingCart().withItems(true).build();

        var setDomainItem = domain.items().stream()
                .map(item -> this.assembler.fromDomain(item))
                .collect(Collectors.toSet());

        var sce = ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart()
                .items(setDomainItem)
                .build();

        domain.removeItem(domain.items().iterator().next().id());

        this.assembler.merge(sce, domain);

        Assertions.assertThat(domain.items().size()).isEqualTo(sce.getItems().size());

    }

}