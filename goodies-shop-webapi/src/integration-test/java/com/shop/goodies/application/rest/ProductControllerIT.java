package com.shop.goodies.application.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.goodies.BaseIntegrationTest;
import com.shop.goodies.application.request.CategoryRequest;
import com.shop.goodies.domain.product.Category;
import com.shop.goodies.domain.product.CategoryRepository;
import com.shop.goodies.domain.user.User;
import com.shop.goodies.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.shop.goodies.infra.tests.UserTestConstants.USER_EMAIL;
import static com.shop.goodies.infra.tests.UserTestConstants.USER_FIRST_NAME;
import static com.shop.goodies.infra.tests.UserTestConstants.USER_LAST_NAME;
import static com.shop.goodies.infra.tests.UserTestConstants.USER_PASSWORD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private User userTest;

    @Test
    @Transactional
    @WithMockUser(username = USER_EMAIL)
    void shouldReturnAllCategories() throws Exception {

        givenUser();
        givenCategory();

        mockMvc.perform(get(BASE_URI + "/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].name").value(categoryTest.getName()));

    }

    @Test
    @Transactional
    @WithMockUser(username = USER_EMAIL)
    void shouldCreatedCategory() throws Exception {
        givenUser();
        CategoryRequest request = CategoryRequest.builder()
                .name("Category")
                .build();

        mockMvc.perform(post(BASE_URI + "/categories")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Category"));
    }

    public void tearDown() {
        if (categoryTest != null) {
            categoryRepository.delete(categoryTest);
        }
    }

    private void givenCategory() {
        categoryTest = Category.builder().name("Category").build();
        categoryRepository.save(categoryTest);
    }

    private void givenUser() {
        userTest = User.builder()
                .email(USER_EMAIL)
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .password(passwordEncoder.encode(USER_PASSWORD))
                .build();

        userRepository.save(userTest);
    }
}
