package com.algaworks.algashop.ordering.infrastructure.persistence;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.algaworks.algashop.ordering.utils.TestcontainerPostgreSQLConfig;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestcontainerPostgreSQLConfig.class, SpringDataAuditingConfig.class})
public abstract class AbstractPersistenceIT {

}
