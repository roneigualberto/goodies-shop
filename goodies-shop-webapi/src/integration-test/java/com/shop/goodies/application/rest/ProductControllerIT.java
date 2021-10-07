package com.shop.goodies.application.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.goodies.BaseIntegrationTest;
import com.shop.goodies.application.request.CategoryRequest;
import com.shop.goodies.application.request.ProductRequest;
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

import static com.shop.goodies.infra.tests.ProductTestConstansts.PRODUCT_AVAILABLE_STOCK;
import static com.shop.goodies.infra.tests.ProductTestConstansts.PRODUCT_CATEGORY_NAME;
import static com.shop.goodies.infra.tests.ProductTestConstansts.PRODUCT_NAME;
import static com.shop.goodies.infra.tests.ProductTestConstansts.PRODUCT_PRICE;
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
    public static final String CATEGORY_API = BASE_URI + "/categories";

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
    private ProductRequest productRequest;
    private CategoryRequest categoryRequest;

    @Test
    @Transactional
    @WithMockUser(username = USER_EMAIL)
    void shouldReturnAllCategories() throws Exception {

        givenUser();
        givenCategory();

        mockMvc.perform(get(CATEGORY_API))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].name").value(categoryTest.getName()));

    }

    @Test
    @Transactional
    @WithMockUser(username = USER_EMAIL)
    void shouldCreatedCategory() throws Exception {
        givenUser();
        givenCategoryRequest();

        mockMvc.perform(post(CATEGORY_API)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(PRODUCT_CATEGORY_NAME));
    }

    private void givenCategoryRequest() {
        categoryRequest = CategoryRequest.builder()
                .name(PRODUCT_CATEGORY_NAME)
                .build();
    }


    @Test
    @Transactional
    @WithMockUser(username = USER_EMAIL)
    void shouldCreatedProduct() throws Exception {
        givenUser();
        givenCategory();
        givenProductRequest();

        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(productRequest.getName()));
    }

    private void givenProductRequest() {
        productRequest = ProductRequest.builder()
                .categoryId(categoryTest.getId())
                .name(PRODUCT_NAME)
                .price(PRODUCT_PRICE)
                .availableStock(PRODUCT_AVAILABLE_STOCK).build();
    }

    private void givenCategory() {
        categoryTest = Category.builder().name(PRODUCT_CATEGORY_NAME).build();
        categoryRepository.save(categoryTest);
    }

    private void givenUser() {
        userTest = User.builder()
                .email(USER_EMAIL)
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .password(passwordEncoder.encode(USER_PASSWORD))
                .build();

        userTest = userRepository.save(userTest);
    }

    public void tearDown() {
        if (categoryTest != null) {
            categoryRepository.delete(categoryTest);
        }

        if (userTest != null) {
            userRepository.delete(userTest);
        }
    }
}
