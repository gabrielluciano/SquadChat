package com.gabrielluciano.squadchat.model.snowflake;

import java.time.Instant;

public interface Snowflake extends Comparable<Snowflake> {

    Instant getInstant();

    long getMachineId();

    long getSequenceNumber();

    long getRawId();
}
