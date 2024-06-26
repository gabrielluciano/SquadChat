package com.gabrielluciano.squadchat.model.snowflake;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class SnowflakeImplTest {

    @Test
    void getRawId_ReturnsId() {
        long id = 1L;
        Snowflake snowflake = new SnowflakeImpl(id);

        assertThat(snowflake.getRawId()).isEqualTo(id);
    }

    @Test
    void getInstant_ReturnsCorrectInstant() {
        long id = 7211531685601517571L;
        Instant expectedInstant = Instant.ofEpochMilli(1719363137627L);

        Snowflake snowflake = new SnowflakeImpl(id);

        assertThat(snowflake.getInstant()).isEqualTo(expectedInstant);
    }

    @Test
    void getMachineId_ReturnsCorrectMachineId() {
        long id = 7211531685601517571L;
        long expectedMachineId = 10L;

        Snowflake snowflake = new SnowflakeImpl(id);

        assertThat(snowflake.getMachineId()).isEqualTo(expectedMachineId);
    }

    @Test
    void getSequenceNumber_ReturnsCorrectSequenceNumber() {
        long id = 7211531685601517571L;
        long expectedSequenceNumber = 3L;

        Snowflake snowflake = new SnowflakeImpl(id);

        assertThat(snowflake.getSequenceNumber()).isEqualTo(expectedSequenceNumber);
    }

    @Test
    void compareTo_WhenSameMachineIdAndSequenceNumber_ShouldOrderByTimestamp() {
        long earlierId = 7211531685601517570L;
        long laterId = 7211531685643460610L;

        Snowflake ealierSnowflake = new SnowflakeImpl(earlierId);
        Snowflake laterSnowflake = new SnowflakeImpl(laterId);

        assertThat(ealierSnowflake.compareTo(laterSnowflake)).isNegative();
    }

    @Test
    void compareTo_WhenSameTimestampAndSequenceNumber_ShouldOrderByMachineId() {
        long smallerId = 7211531685643440130L;
        long higherId = 7211531685643460610L;

        Snowflake smallerSnowflake = new SnowflakeImpl(smallerId);
        Snowflake higherSnowflake = new SnowflakeImpl(higherId);

        assertThat(smallerSnowflake.compareTo(higherSnowflake)).isNegative();
    }

    @Test
    void compareTo_WhenSameTimestampAndMachineId_ShouldOrderBySequenceNumber() {
        long earlierId = 7211531685643460608L;
        long laterId = 7211531685643460609L;

        Snowflake ealierSnowflake = new SnowflakeImpl(earlierId);
        Snowflake laterSnowflake = new SnowflakeImpl(laterId);

        assertThat(ealierSnowflake.compareTo(laterSnowflake)).isNegative();
    }
}
