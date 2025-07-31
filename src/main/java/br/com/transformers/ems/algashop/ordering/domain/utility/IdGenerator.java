package br.com.transformers.ems.algashop.ordering.domain.utility;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;

import io.hypersistence.tsid.TSID;

import java.util.UUID;

public class IdGenerator {
    private IdGenerator() {
    }

    private static final TimeBasedEpochRandomGenerator TIME_BASED_EPOCH_RANDOM_GENERATOR = Generators.timeBasedEpochRandomGenerator();

    private static final TSID.Factory TSID_FACTORY = TSID.Factory.INSTANCE;

    public static UUID generateUUID() {
        return TIME_BASED_EPOCH_RANDOM_GENERATOR.generate();
    }

    /**
     * TSID_NODE
     * TSID_NODE_COUNT
     * 
     * @return
     */
    public static TSID generateTSID() {
        return TSID_FACTORY.generate();
    }
}
