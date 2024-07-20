package com.gabrielluciano.squadchat.model.dto;

import com.gabrielluciano.squadchat.model.entities.Server;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class ServerResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private Instant createdAt;
    private String pictureUrl;

    private UserResponse owner;

    public ServerResponse() {
    }

    public ServerResponse(UUID id, String name, Instant createdAt, String pictureUrl, UserResponse owner) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.pictureUrl = pictureUrl;
        this.owner = owner;
    }

    public static ServerResponse fromServer(Server server) {
        return new ServerResponse(server.getId(), server.getName(), server.getCreatedAt(), server.getPictureUrl(),
                UserResponse.fromUser(server.getOwner()));
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setOwner(UserResponse owner) {
        this.owner = owner;
    }

    public UserResponse getOwner() {
        return owner;
    }
}
