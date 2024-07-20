package com.gabrielluciano.squadchat.services;

import com.gabrielluciano.squadchat.model.dto.ServerCreateRequest;
import com.gabrielluciano.squadchat.model.dto.ServerResponse;
import com.gabrielluciano.squadchat.model.entities.Server;
import com.gabrielluciano.squadchat.model.entities.User;
import com.gabrielluciano.squadchat.repository.ServerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class ServerService {

    private final ServerRepository repository;

    public ServerService(ServerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ServerResponse createServer(ServerCreateRequest serverCreateRequest, User owner) {
        if (owner == null)
            throw new IllegalArgumentException("Owner cannot be null");
        Server serverToSave = new Server(UUID.randomUUID(), serverCreateRequest.getName(), Instant.now(), null, owner);
        return ServerResponse.fromServer(repository.save(serverToSave));
    }
}
