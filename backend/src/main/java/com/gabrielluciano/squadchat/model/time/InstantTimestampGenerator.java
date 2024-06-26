package com.gabrielluciano.squadchat.model.time;

import java.time.Instant;

public class InstantTimestampGenerator implements TimestampGenerator {

    @Override
    public Instant now() {
        return Instant.now();
    }
}
