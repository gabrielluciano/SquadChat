package com.gabrielluciano.squadchat.model.snowflake;

import java.time.Instant;

import com.gabrielluciano.squadchat.model.time.InstantTimestampGenerator;
import com.gabrielluciano.squadchat.model.time.TimestampGenerator;

public final class SnowflakeIdGenerator {

    private static final int MACHINE_ID_BITS = 12;
    private static final int TIMESTAMP_BITS = 22;

    private static SnowflakeIdGenerator INSTANCE;

    private TimestampGenerator timestampGenerator;
    private long sequenceNumber = 0;
    private long machineId;
    private Instant lastTimestamp;

    private SnowflakeIdGenerator() {
        timestampGenerator = new InstantTimestampGenerator();
        machineId = 1;
    }

    public static SnowflakeIdGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SnowflakeIdGenerator();
        }
        return INSTANCE;
    }

    public void setTimestampGenerator(TimestampGenerator timestampGenerator) {
        this.timestampGenerator = timestampGenerator;
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
}
