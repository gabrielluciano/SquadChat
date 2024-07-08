package com.gabrielluciano.squadchat.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielluciano.squadchat.model.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
