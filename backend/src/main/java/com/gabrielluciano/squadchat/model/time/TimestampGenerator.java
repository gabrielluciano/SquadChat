package com.gabrielluciano.squadchat.model.time;

import java.time.Instant;

public interface TimestampGenerator {

    Instant now();
}
