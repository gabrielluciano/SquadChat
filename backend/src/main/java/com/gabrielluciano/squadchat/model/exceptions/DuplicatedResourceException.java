package com.gabrielluciano.squadchat.model.exceptions;

public class DuplicatedResourceException extends RuntimeException {

    public DuplicatedResourceException(String message) {
        super(message);
    }
}
