package com.gabrielluciano.squadchat.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.gabrielluciano.squadchat.model.entities.Message;
import com.gabrielluciano.squadchat.model.snowflake.SnowflakeIdGenerator;
import com.gabrielluciano.squadchat.repository.MessageRepository;

@Component
@Profile("dev")
public class DevDataSourceConfig implements CommandLineRunner {

    private final SnowflakeIdGenerator generator;
    private final MessageRepository messageRepository;

    public DevDataSourceConfig(
            SnowflakeIdGenerator generator,
            MessageRepository messageRepository) {
        this.generator = generator;
        this.messageRepository = messageRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Message message1 = new Message(generator.generateId(), "Hello World!", false, null, null);
        Message message2 = new Message(generator.generateId(), "How are you?", true, null, null);
        Message message3 = new Message(generator.generateId(), "Good morning!", false, null, null);
        Message message4 = new Message(generator.generateId(), "Let's meet at 5 PM", true, null, null);

        messageRepository.saveAll(List.of(message1, message2, message3, message4));
    }
}
