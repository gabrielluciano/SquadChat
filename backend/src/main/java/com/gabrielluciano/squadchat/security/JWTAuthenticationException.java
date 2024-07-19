package com.gabrielluciano.squadchat.security;

import org.springframework.security.core.AuthenticationException;

public class JWTAuthenticationException extends AuthenticationException {
    public JWTAuthenticationException(String message) {
        super(message);
    }
}
