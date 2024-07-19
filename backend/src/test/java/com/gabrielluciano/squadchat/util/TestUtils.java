package com.gabrielluciano.squadchat.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {

    private TestUtils() {
    }

    public static String asJsonString(final Object object) throws Exception {
        return new ObjectMapper().writeValueAsString(object);
    }
}
