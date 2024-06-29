package com.gabrielluciano.squadchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielluciano.squadchat.model.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
