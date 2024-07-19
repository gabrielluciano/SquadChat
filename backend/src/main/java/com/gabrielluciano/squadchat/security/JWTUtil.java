package com.gabrielluciano.squadchat.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class JWTUtil {

    private final String issuer;
    private final long expiration;
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JWTUtil(@Value("${jwt.token.secret}") String secret,
                   @Value("${jwt.token.issuer}") String issuer,
                   @Value("${jwt.token.expiration}") String expirationString
    ) {
        if (secret == null || issuer == null || expirationString == null)
            throw new IllegalStateException("secret, issuer and expiration cannot be null");

        this.issuer = issuer;
        this.expiration = parseExpiration(expirationString);
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm)
                .acceptExpiresAt(expiration)
                .withIssuer(issuer)
                .build();
    }

    public DecodedJWT decodeToken(String token) throws JWTVerificationException {
        return verifier.verify(token);
    }

    public String createToken(SecurityUser user) {
        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(Duration.ofSeconds(expiration)))
                .withClaim("username", user.getUsername())
                .withClaim("uuid", user.getId().toString())
                .sign(algorithm);
    }

    private long parseExpiration(String expirationString) {
        try {
            return Long.parseLong(expirationString);
        } catch (NumberFormatException ex) {
            throw new IllegalStateException("Invalid token expiration value set in 'jwt.token.expiration' property");
        }
    }
}
