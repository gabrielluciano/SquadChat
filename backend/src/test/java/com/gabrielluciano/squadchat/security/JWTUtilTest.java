package com.gabrielluciano.squadchat.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.gabrielluciano.squadchat.model.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class JWTUtilTest {

    public JWTUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JWTUtil("secret", "application", "60");
    }

    @Test
    void createToken_ShouldCreateJWTToken() throws Exception {
        SecurityUser user = new SecurityUser(new User(UUID.randomUUID(), "username", "pass", Instant.now(), null));

        String token = jwtUtil.createToken(user);

        assertThat(token).isNotBlank();
    }

    @Test
    void decodeToken_ShouldDecodeJWTToken() throws Exception {
        UUID id = UUID.randomUUID();
        String username = "username";
        SecurityUser user = new SecurityUser(new User(id, username, "pass", Instant.now(), null));

        String token = jwtUtil.createToken(user);
        DecodedJWT decodedJWT = jwtUtil.decodeToken(token);

        assertThat(decodedJWT.getClaim("uuid").asString()).isEqualTo(id.toString());
        assertThat(decodedJWT.getClaim("username").asString()).isEqualTo(username);
        assertThat(decodedJWT.getIssuer()).isEqualTo("application");
    }

    @Test
    void jwtUtil_ShouldThrowErrorIfSecretIsNull() throws Exception {

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> new JWTUtil(null, "app", "60"));
    }

    @Test
    void jwtUtil_ShouldThrowErrorIfIssuerIsNull() throws Exception {

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> new JWTUtil("secret", null, "60"));
    }

    @Test
    void jwtUtil_ShouldThrowErrorIfExpirationStringIsNull() throws Exception {

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> new JWTUtil("secret", "app", null));
    }

    @Test
    void jwtUtil_ShouldThrowErrorIfExpirationStringCannotBeParsedAsLong() throws Exception {

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> new JWTUtil("secret", "app", "text"));
    }
}
