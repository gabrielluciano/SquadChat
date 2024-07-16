package com.gabrielluciano.squadchat.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gabrielluciano.squadchat.model.dto.UserCreateRequest;
import com.gabrielluciano.squadchat.model.dto.UserCreateResponse;
import com.gabrielluciano.squadchat.model.entities.User;
import com.gabrielluciano.squadchat.model.exceptions.DuplicatedResourceException;
import com.gabrielluciano.squadchat.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {
        Optional<User> userFromDb = repository.findByUsername(userCreateRequest.getUsername());
        if (userFromDb.isPresent())
            throw new DuplicatedResourceException("Username already exists");

        User userToSave = new User(UUID.randomUUID(), userCreateRequest.getUsername(),
                passwordEncoder.encode(userCreateRequest.getPassword()), Instant.now(), null);

        return UserCreateResponse.fromUser(repository.save(userToSave));
    }
}
