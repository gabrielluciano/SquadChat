package com.gabrielluciano.squadchat.services;

import com.gabrielluciano.squadchat.model.dto.LoginRequest;
import com.gabrielluciano.squadchat.model.dto.UserCreateRequest;
import com.gabrielluciano.squadchat.model.dto.UserCreateResponse;
import com.gabrielluciano.squadchat.model.entities.User;
import com.gabrielluciano.squadchat.model.exceptions.DuplicatedResourceException;
import com.gabrielluciano.squadchat.model.exceptions.InvalidCredentialsException;
import com.gabrielluciano.squadchat.repository.UserRepository;
import com.gabrielluciano.squadchat.security.JWTUtil;
import com.gabrielluciano.squadchat.security.SecurityUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {
        Optional<User> userFromDb = repository.findByUsername(userCreateRequest.getUsername());
        if (userFromDb.isPresent())
            throw new DuplicatedResourceException("Username already exists");

        User userToSave = new User(UUID.randomUUID(), userCreateRequest.getUsername(),
                passwordEncoder.encode(userCreateRequest.getPassword()), Instant.now(), null);

        return UserCreateResponse.fromUser(repository.save(userToSave));
    }

    public String login(LoginRequest loginRequest) {
        User user = repository.findByUsername(loginRequest.getUsername())
                .orElseThrow(InvalidCredentialsException::new);

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            return jwtUtil.createToken(new SecurityUser(user));

        throw new InvalidCredentialsException();
    }
}
