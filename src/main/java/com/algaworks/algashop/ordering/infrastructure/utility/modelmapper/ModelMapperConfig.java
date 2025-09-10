package com.algaworks.algashop.ordering.infrastructure.utility.modelmapper;

import java.time.LocalDate;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algashop.ordering.application.management.CustomerOutput;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.commons.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.valueobject.BirthDate;

@Configuration
public class ModelMapperConfig {
    
    private static final Converter<FullName, String> firstNameToStringConverter = mappingContext -> {

        if (mappingContext.getSource() == null) {
            return null;
        }

        return mappingContext.getSource().firstName();

    };

    private static final Converter<FullName, String> lastNameToStringConverter = mappingContext -> {

        if (mappingContext.getSource() == null) {
            return null;
        }

        return mappingContext.getSource().lastName();

    };

    private static final Converter<BirthDate, LocalDate> birthDateToLocalDateConverter = mappingContext -> {

        if (mappingContext.getSource() == null) {
            return null;
        }

        return mappingContext.getSource().value();

    };

    @Bean
    public Mapper mapper() {

        ModelMapper modelMapper = new ModelMapper();

        configuration(modelMapper);

        return modelMapper::map;

    }

    private void configuration(ModelMapper modelMapper) {

        modelMapper.getConfiguration()
            .setSourceNamingConvention(org.modelmapper.convention.NamingConventions.NONE)
            .setDestinationNamingConvention(org.modelmapper.convention.NamingConventions.NONE)
            .setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);

        modelMapper.createTypeMap(Customer.class, CustomerOutput.class)
            .addMappings(mapping ->  mapping.using(firstNameToStringConverter)
                .map(Customer::fullName, CustomerOutput::setFirstName))
            .addMappings(mapping ->  mapping.using(lastNameToStringConverter)
                .map(Customer::fullName, CustomerOutput::setLastName))
            .addMappings(mapping ->  mapping.using(birthDateToLocalDateConverter)
                .map(Customer::birthDate, CustomerOutput::setBirthDate))
        ;

    }   
}
