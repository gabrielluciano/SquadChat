package com.gabrielluciano.squadchat.resources.controllers;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielluciano.squadchat.model.dto.UserCreateRequest;
import com.gabrielluciano.squadchat.repository.UserRepository;
import com.gabrielluciano.squadchat.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void createUser_ShouldCreateUser() throws Exception {
        UserCreateRequest userCreateRequest = new UserCreateRequest("username", "12345");

        mockMvc.perform(post("/api/v1/users/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userCreateRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value(userCreateRequest.getUsername()))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.avatarUrl", emptyOrNullString()));
    }

    @Test
    void createUser_ShouldThrowDuplicatedResourceException() throws Exception {
        UserCreateRequest userCreateRequest = new UserCreateRequest("username", "12345");
        userService.createUser(userCreateRequest);

        mockMvc.perform(post("/api/v1/users/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userCreateRequest)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Duplicated Resource"))
                .andExpect(jsonPath("$.message").value("Username already exists"))
                .andExpect(jsonPath("$.path").value("/api/v1/users/new"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()));
    }

    private static String asJsonString(final Object object) throws Exception {
        return new ObjectMapper().writeValueAsString(object);
    }
}
