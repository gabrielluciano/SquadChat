package com.gabrielluciano.squadchat.resources.controllers;

import com.gabrielluciano.squadchat.model.dto.ServerCreateRequest;
import com.gabrielluciano.squadchat.model.dto.ServerResponse;
import com.gabrielluciano.squadchat.model.entities.User;
import com.gabrielluciano.squadchat.security.SecurityUser;
import com.gabrielluciano.squadchat.services.ServerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/servers")
public class ServerController {

    private final ServerService service;

    public ServerController(ServerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ServerResponse> createServer(@RequestBody ServerCreateRequest serverCreateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((SecurityUser) authentication.getPrincipal()).getUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createServer(serverCreateRequest, user));
    }
}
