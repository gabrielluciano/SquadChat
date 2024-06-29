package com.gabrielluciano.squadchat.model.time;

import java.time.Instant;

import org.springframework.stereotype.Component;

@Component
public class InstantTimestampGenerator implements TimestampGenerator {

    @Override
    public Instant now() {
        return Instant.now();
    }
}
