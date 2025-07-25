package br.com.transformers.ems.algashop.ordering.domain.utility;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;

import java.util.UUID;

public class IdGenerator {
    private IdGenerator() {
    }

    private static final TimeBasedEpochRandomGenerator TIME_BASED_EPOCH_RANDOM_GENERATOR = Generators.timeBasedEpochRandomGenerator();

    public static UUID generate() {
        return TIME_BASED_EPOCH_RANDOM_GENERATOR.generate();
    }
}
