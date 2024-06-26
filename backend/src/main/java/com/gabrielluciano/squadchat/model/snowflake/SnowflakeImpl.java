package com.gabrielluciano.squadchat.model.snowflake;

import java.time.Instant;

public class SnowflakeImpl implements Snowflake {

    private static final long MACHINE_SEQUENCE_NUMBER_MASK = 0b111111111111L;
    private static final long MACHINE_ID_MASK = 0b1111111111L;
    private static final int MACHINE_ID_BITS = 12;
    private static final int TIMESTAMP_BITS = 22;

    private final Long id;

    public SnowflakeImpl(Long id) {
        this.id = id;
    }

    @Override
    public Instant getInstant() {
        return Instant.ofEpochMilli(id >> TIMESTAMP_BITS);
    }

    @Override
    public long getMachineId() {
        return (id >> MACHINE_ID_BITS) & MACHINE_ID_MASK;
    }

    @Override
    public long getSequenceNumber() {
        return id & MACHINE_SEQUENCE_NUMBER_MASK;
    }

    @Override
    public long getRawId() {
        return id;
    }

    @Override
    public int compareTo(Snowflake other) {
        return id.compareTo(other.getRawId());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SnowflakeImpl other = (SnowflakeImpl) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
