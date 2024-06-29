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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "servers")
public class Server implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    private String name;
    private Instant createdAt;
    private String pictureUrl;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(name = "servers_users", joinColumns = @JoinColumn(name = "server_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "servers_admins", joinColumns = @JoinColumn(name = "server_id"), inverseJoinColumns = @JoinColumn(name = "admin_id"))
    private Set<User> admins = new HashSet<>();

    @OneToMany(mappedBy = "server")
    private Set<Room> rooms = new HashSet<>();

    public Server() {
        id = UUID.randomUUID();
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public Set<User> getAdmins() {
        return admins;
    }

    public boolean addAdmin(User user) {
        return admins.add(user);
    }

    public boolean removeAdmin(User user) {
        return admins.remove(user);
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public boolean addRoom(Room room) {
        return rooms.add(room);
    }

    public boolean removeRoom(Room room) {
        return rooms.remove(room);
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
        Server other = (Server) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
