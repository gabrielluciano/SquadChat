package com.gabrielluciano.squadchat.model.snowflake;

import java.io.Serializable;
import java.time.Instant;

public interface Snowflake extends Comparable<Snowflake>, Serializable {

    Instant getInstant();

    long getMachineId();

    long getSequenceNumber();

    long getRawId();
}
