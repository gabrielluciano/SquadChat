package com.gabrielluciano.squadchat.model.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    private String name;
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;

    @ManyToMany
    @JoinTable(name = "rooms_users", joinColumns = @JoinColumn(name = "room_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    public Room() {
        id = UUID.randomUUID();
    }

    public Room(UUID id, String name, Instant createdAt, Server server) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.server = server;
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

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Set<User> getUsers() {
        return users;
    }

    public boolean addUser(User user) {
        return users.add(user);
    }

    public boolean removeUser(User user) {
        return users.remove(user);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Room other = (Room) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
