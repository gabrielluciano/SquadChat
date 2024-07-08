package com.gabrielluciano.squadchat.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielluciano.squadchat.model.entities.Server;

public interface ServerRepository extends JpaRepository<Server, UUID> {

}
