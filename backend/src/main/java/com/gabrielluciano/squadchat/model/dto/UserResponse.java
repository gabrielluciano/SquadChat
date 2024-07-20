package com.gabrielluciano.squadchat.model.dto;

import java.time.Instant;
import java.util.UUID;

import com.gabrielluciano.squadchat.model.entities.User;

public class UserResponse {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String username;
    private Instant createdAt;
    private String avatarUrl;

    public UserResponse() {
    }

    public UserResponse(UUID id, String username, Instant createdAt, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.avatarUrl = avatarUrl;
    }

    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getCreatedAt(), user.getAvatarUrl());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
