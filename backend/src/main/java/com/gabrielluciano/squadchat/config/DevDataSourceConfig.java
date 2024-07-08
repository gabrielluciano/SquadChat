package com.gabrielluciano.squadchat.config;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.gabrielluciano.squadchat.model.entities.Message;
import com.gabrielluciano.squadchat.model.entities.Room;
import com.gabrielluciano.squadchat.model.entities.Server;
import com.gabrielluciano.squadchat.model.entities.User;
import com.gabrielluciano.squadchat.model.snowflake.SnowflakeIdGenerator;
import com.gabrielluciano.squadchat.repository.MessageRepository;
import com.gabrielluciano.squadchat.repository.RoomRepository;
import com.gabrielluciano.squadchat.repository.ServerRepository;
import com.gabrielluciano.squadchat.repository.UserRepository;

@Component
@Profile("dev")
public class DevDataSourceConfig implements CommandLineRunner {

    private final SnowflakeIdGenerator generator;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ServerRepository serverRepository;
    private final RoomRepository roomRepository;

    public DevDataSourceConfig(
            SnowflakeIdGenerator generator,
            MessageRepository messageRepository,
            UserRepository userRepository,
            ServerRepository serverRepository,
            RoomRepository roomRepository) {
        this.generator = generator;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.serverRepository = serverRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User(UUID.randomUUID(), "john_doe", "123", Instant.now(), "");
        User user2 = new User(UUID.randomUUID(), "jane_smith", "123", Instant.now(), "");
        User user3 = new User(UUID.randomUUID(), "michael_brown", "123", Instant.now(), "");
        User user4 = new User(UUID.randomUUID(), "emily_white", "123", Instant.now(), "");

        userRepository.saveAll(List.of(user1, user2, user3, user4));

        Server server1 = new Server(UUID.randomUUID(), "Company Server", Instant.now(), "", user1);
        Server server2 = new Server(UUID.randomUUID(), "Gaming Server", Instant.now(), "", user2);

        Room room1 = new Room(UUID.randomUUID(), "General Chat", Instant.now(), server1);
        Room room2 = new Room(UUID.randomUUID(), "Tech Talk", Instant.now(), server1);
        Room room3 = new Room(UUID.randomUUID(), "Gaming", Instant.now(), server2);
        Room room4 = new Room(UUID.randomUUID(), "Random", Instant.now(), server2);

        server1.addUser(user2);
        server1.addUser(user3);
        server2.addUser(user4);

        server1.addAdmin(user3);

        room1.addUser(user2);
        room1.addUser(user3);
        room2.addUser(user3);
        room3.addUser(user4);

        serverRepository.saveAll(List.of(server1, server2));
        roomRepository.saveAll(List.of(room1, room2, room3, room4));

        Message message1 = new Message(generator.generateId(), "Hello World!", false, user1, room1);
        Message message2 = new Message(generator.generateId(), "How are you?", true, user3, room1);
        Message message3 = new Message(generator.generateId(), "Good morning!", false, user2, room2);
        Message message4 = new Message(generator.generateId(), "Let's meet at 5 PM", true, user3, room3);

        messageRepository.saveAll(List.of(message1, message2, message3, message4));
    }
}
