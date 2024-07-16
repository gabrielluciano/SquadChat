package com.gabrielluciano.squadchat.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gabrielluciano.squadchat.model.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);
}
