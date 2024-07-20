package com.gabrielluciano.squadchat.resources.controllers;

import com.gabrielluciano.squadchat.model.dto.ServerCreateRequest;
import com.gabrielluciano.squadchat.model.entities.User;
import com.gabrielluciano.squadchat.repository.UserRepository;
import com.gabrielluciano.squadchat.security.SecurityUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.UUID;

import static com.gabrielluciano.squadchat.util.TestUtils.asJsonString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext
class ServerControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void createServer_ShouldCreateServer() throws Exception {
        User user = new User(UUID.randomUUID(), "john", passwordEncoder.encode("123"), Instant.now(), null);
        userRepository.saveAndFlush(user);

        ServerCreateRequest serverCreateRequest = new ServerCreateRequest("My Server");

        mockMvc.perform(post("/api/v1/servers")
                        .with(user(new SecurityUser(user)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(serverCreateRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(serverCreateRequest.getName()))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.pictureUrl", emptyOrNullString()))
                .andExpect(jsonPath("$.owner.username").value(user.getUsername()))
                .andExpect(jsonPath("$.owner.id").value(user.getId().toString()));

    }
}
