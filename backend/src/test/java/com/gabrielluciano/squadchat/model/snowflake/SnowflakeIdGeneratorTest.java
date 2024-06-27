package com.gabrielluciano.squadchat.model.snowflake;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import com.gabrielluciano.squadchat.model.time.InstantTimestampGenerator;
import com.gabrielluciano.squadchat.model.time.TimestampGenerator;

class SnowflakeIdGeneratorTest {

    private SnowflakeIdGenerator snowflakeIdGenerator;
    private FixedTimestampGenerator timestampGenerator;
    private MockEnvironment mockEnvironment;

    @BeforeEach
    void setUp() {
        timestampGenerator = new FixedTimestampGenerator();
        mockEnvironment = new MockEnvironment();
        mockEnvironment.setProperty("snowflake.machine_id", "1");

        snowflakeIdGenerator = new SnowflakeIdGenerator(timestampGenerator, mockEnvironment);
    }

    @Test
    void generateId_WhenSameTimestamp_ShouldIncreaseSequenceNumber() {
        timestampGenerator.setInstant(Instant.now());

        Snowflake earlierSnowflake = snowflakeIdGenerator.generateId();
        Snowflake laterSnowflake = snowflakeIdGenerator.generateId();

        assertThat(earlierSnowflake.getMachineId()).isEqualTo(1L);
        assertThat(earlierSnowflake.getInstant()).isEqualTo(laterSnowflake.getInstant());
        assertThat(earlierSnowflake.getSequenceNumber()).isZero();
        assertThat(laterSnowflake.getSequenceNumber()).isEqualTo(1);
    }

    @Test
    void generateId_WhenDifferentTimestamp_ShouldHaveSequenceNumberZero() {
        long timestamp = 1719363137637L;

        timestampGenerator.setInstant(Instant.ofEpochMilli(timestamp));
        Snowflake earlierSnowflake = snowflakeIdGenerator.generateId();

        timestampGenerator.setInstant(Instant.ofEpochMilli(timestamp + 1));
        Snowflake laterSnowflake = snowflakeIdGenerator.generateId();

        assertThat(earlierSnowflake.getInstant()).isNotEqualTo(laterSnowflake.getInstant());
        assertThat(earlierSnowflake.getSequenceNumber()).isZero();
        assertThat(laterSnowflake.getSequenceNumber()).isZero();
    }

    @Test
    void generateId_WhenRaceCondition_ShouldWorkCorrectly() throws Exception {
        int count = 1000;
        Set<Snowflake> uniqueSnowflakes = getUniqueSnowflakes(
                new SnowflakeIdGenerator(new InstantTimestampGenerator(), mockEnvironment), count);
        assertThat(uniqueSnowflakes).hasSize(count);
    }

    @Test
    void constructor_WhenSnowflakeMachineIdIsNotValid_ShouldThrowException() {
        MockEnvironment env = new MockEnvironment();
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
            new SnowflakeIdGenerator(timestampGenerator, env);
        });

        env.setProperty("snowflake.machine_id", "text");
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
            new SnowflakeIdGenerator(timestampGenerator, env);
        });
    }

    private Set<Snowflake> getUniqueSnowflakes(SnowflakeIdGenerator generator, int count) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Set<Snowflake> uniqueSnowflakes = new LinkedHashSet<>();
        List<Future<Snowflake>> futures = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            futures.add(executor.submit(generator::generateId));
        }

        for (Future<Snowflake> future : futures) {
            uniqueSnowflakes.add(future.get());
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        return uniqueSnowflakes;
    }

    static final class FixedTimestampGenerator implements TimestampGenerator {

        private Instant instant;

        @Override
        public Instant now() {
            return instant;
        }

        public void setInstant(Instant instant) {
            this.instant = instant;
        }
    }
}
