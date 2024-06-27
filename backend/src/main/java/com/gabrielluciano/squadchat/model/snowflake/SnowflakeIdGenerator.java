package com.gabrielluciano.squadchat.model.snowflake;

import java.time.Instant;

import org.springframework.core.env.Environment;

import com.gabrielluciano.squadchat.model.time.TimestampGenerator;

public class SnowflakeIdGenerator {

    private static final String SNOWFLAKE_MACHINE_ID_PROPERTY = "snowflake.machine_id";
    private static final int MACHINE_ID_BITS = 12;
    private static final int TIMESTAMP_BITS = 22;

    private final TimestampGenerator timestampGenerator;
    private final Environment environment;

    private static long sequenceNumber = 0;

    private Instant lastTimestamp;
    private long machineId;

    public SnowflakeIdGenerator(TimestampGenerator timestampGenerator, Environment environment) {
        this.timestampGenerator = timestampGenerator;
        this.environment = environment;
        this.machineId = getMachineId();
    }

    public synchronized Snowflake generateId() {
        Instant now = timestampGenerator.now();
        if (lastTimestamp == null || now.toEpochMilli() > lastTimestamp.toEpochMilli()) {
            sequenceNumber = 0;
            lastTimestamp = now;
        } else {
            sequenceNumber++;
        }

        long id = (now.toEpochMilli() << TIMESTAMP_BITS) | (machineId << MACHINE_ID_BITS) | sequenceNumber;
        return new SnowflakeImpl(id);
    }

    private long getMachineId() {
        try {
            String str = environment.getProperty(SNOWFLAKE_MACHINE_ID_PROPERTY);
            long machineId = Long.parseLong(environment.getProperty(SNOWFLAKE_MACHINE_ID_PROPERTY));
            return machineId;
        } catch (Exception e) {
            throw new IllegalStateException(
                    String.format("%s property is not defined or is not a valid long", SNOWFLAKE_MACHINE_ID_PROPERTY));
        }
    }
}
