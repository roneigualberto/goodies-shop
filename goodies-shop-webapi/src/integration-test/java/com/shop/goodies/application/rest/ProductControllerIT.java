package com.shop.goodies.application.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.goodies.BaseIntegrationTest;
import com.shop.goodies.domain.product.Category;
import com.shop.goodies.domain.product.CategoryRepository;
import com.shop.goodies.domain.user.User;
import com.shop.goodies.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.shop.goodies.infra.tests.UserTestConstants.USER_EMAIL;
import static com.shop.goodies.infra.tests.UserTestConstants.USER_FIRST_NAME;
import static com.shop.goodies.infra.tests.UserTestConstants.USER_LAST_NAME;
import static com.shop.goodies.infra.tests.UserTestConstants.USER_PASSWORD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ProductControllerIT extends BaseIntegrationTest {


    public static final String BASE_URI = "/api/v1/product";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private Category categoryTest;


    @Test
    @Transactional
    @WithMockUser(username = USER_EMAIL)
    void shouldAutenticatedByCredential() throws Exception {

        User user = User.builder()
                .email(USER_EMAIL)
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .password(passwordEncoder.encode(USER_PASSWORD))
                .build();

        userRepository.save(user);

        categoryTest = Category.builder().name("Category").build();

        categoryRepository.save(categoryTest);

        mockMvc.perform(get(BASE_URI + "/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].name").value(categoryTest.getName()));

    }

    public void tearDown() {
        if (categoryTest != null) {
            categoryRepository.delete(categoryTest);
        }
    }
}
