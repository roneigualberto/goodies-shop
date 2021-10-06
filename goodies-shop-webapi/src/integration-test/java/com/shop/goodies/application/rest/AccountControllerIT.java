package com.shop.goodies.application.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.goodies.BaseIntegrationTest;
import com.shop.goodies.application.request.UserRequest;
import com.shop.goodies.domain.user.User;
import com.shop.goodies.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AccountControllerIT extends BaseIntegrationTest {


    private static final String USER_EMAIL = "user" + UUID.randomUUID() + "@email.com";
    private static final String USER_FIRST_NAME = "User";
    private static final String USER_LAST_NAME = "User";
    private static final String USER_PASSWORD = "Password";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private User userTest;


    @Test
    void shouldCreatedAccount() throws Exception {
        UserRequest request = UserRequest.builder()
                .email(USER_EMAIL)
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .password(USER_PASSWORD)
                .build();

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.firstName").value(USER_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(USER_LAST_NAME));

        Optional<User> optUser = userRepository.findByEmail(USER_EMAIL);
        assertThat(optUser.isPresent()).isTrue();
        userTest = optUser.get();
    }


    @AfterEach
    public void tierDown() {
        if (userTest != null) {
            userRepository.delete(userTest);
        }
    }

}
