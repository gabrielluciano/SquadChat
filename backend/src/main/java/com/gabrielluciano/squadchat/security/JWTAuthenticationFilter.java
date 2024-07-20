package com.gabrielluciano.squadchat.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    public JWTAuthenticationFilter(UserDetailsService userDetailsService, JWTUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getToken(request);
            DecodedJWT decodedJWT = decodeToken(token);
            String username = decodedJWT.getClaim("username").asString();

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken
                    .authenticated(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JWTAuthenticationException ignore) {
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private String getToken(HttpServletRequest request) {
        final String PREFIX = "Bearer ";
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith(PREFIX))
            throw new JWTAuthenticationException("Token not found");

        return authorizationHeader.replace(PREFIX, "");
    }

    private DecodedJWT decodeToken(String token) {
        try {
            return jwtUtil.decodeToken(token);
        } catch (TokenExpiredException ex) {
            throw new JWTAuthenticationException("Token expired on: " + ex.getExpiredOn());
        } catch (JWTVerificationException ex) {
            throw new JWTAuthenticationException("Invalid token");
        }
    }
}
