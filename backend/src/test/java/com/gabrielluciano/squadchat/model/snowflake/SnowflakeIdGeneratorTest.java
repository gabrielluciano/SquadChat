package com.gabrielluciano.squadchat.model.snowflake;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.gabrielluciano.squadchat.model.time.TimestampGenerator;

class SnowflakeIdGeneratorTest {

    @Test
    void generateId_WhenSameTimestamp_ShouldIncreaseSequenceNumber() {
        FixedTimestampGenerator timestampGenerator = new FixedTimestampGenerator();
        timestampGenerator.setInstant(Instant.now());

        SnowflakeIdGenerator snowflakeGenerator = SnowflakeIdGenerator.getInstance();
        snowflakeGenerator.setTimestampGenerator(timestampGenerator);

        Snowflake earlierSnowflake = snowflakeGenerator.generateId();
        Snowflake laterSnowflake = snowflakeGenerator.generateId();

        assertThat(earlierSnowflake.getInstant()).isEqualTo(laterSnowflake.getInstant());
        assertThat(earlierSnowflake.getSequenceNumber()).isEqualTo(0);
        assertThat(laterSnowflake.getSequenceNumber()).isEqualTo(1);
    }

    @Test
    void generateId_WhenDifferentTimestamp_ShouldHaveSequenceNumberZero() {
        long timestamp = 1719363137637L;

        FixedTimestampGenerator timestampGenerator = new FixedTimestampGenerator();
        SnowflakeIdGenerator snowflakeGenerator = SnowflakeIdGenerator.getInstance();
        snowflakeGenerator.setTimestampGenerator(timestampGenerator);

        timestampGenerator.setInstant(Instant.ofEpochMilli(timestamp));
        Snowflake earlierSnowflake = snowflakeGenerator.generateId();

        timestampGenerator.setInstant(Instant.ofEpochMilli(timestamp + 1));
        Snowflake laterSnowflake = snowflakeGenerator.generateId();

        assertThat(earlierSnowflake.getInstant()).isNotEqualTo(laterSnowflake.getInstant());
        assertThat(earlierSnowflake.getSequenceNumber()).isEqualTo(0);
        assertThat(laterSnowflake.getSequenceNumber()).isEqualTo(0);
    }

    @Test
    void generateId_WhenRaceCondition_ShouldWorkCorrectly() throws Exception {
        int count = 1000;
        Set<Snowflake> uniqueSnowflakes = getUniqueSnowflakes(SnowflakeIdGenerator.getInstance(), count);
        assertThat(uniqueSnowflakes.size()).isEqualTo(count);
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
