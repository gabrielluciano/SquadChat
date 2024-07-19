package com.gabrielluciano.squadchat.resources.controllers;

import com.gabrielluciano.squadchat.model.dto.LoginRequest;
import com.gabrielluciano.squadchat.model.dto.UserCreateRequest;
import com.gabrielluciano.squadchat.model.dto.UserCreateResponse;
import com.gabrielluciano.squadchat.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("new")
    public ResponseEntity<UserCreateResponse> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(userCreateRequest));
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(service.login(loginRequest));
    }
}
