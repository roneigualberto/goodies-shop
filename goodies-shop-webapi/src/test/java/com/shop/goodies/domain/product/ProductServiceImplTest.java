package com.shop.goodies.domain.product;

import com.shop.goodies.domain.user.User;
import com.shop.goodies.domain.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.shop.goodies.infra.tests.ProductTestConstansts.PRODUCT_CATEGORY_ID;
import static com.shop.goodies.infra.tests.ProductTestConstansts.PRODUCT_CATEGORY_NAME;
import static com.shop.goodies.infra.tests.ProductTestConstansts.PRODUCT_NAME;
import static com.shop.goodies.infra.tests.ProductTestConstansts.PRODUCT_PRICE;
import static com.shop.goodies.infra.tests.UserTestConstants.USER_EMAIL;
import static com.shop.goodies.infra.tests.UserTestConstants.USER_FIRST_NAME;
import static com.shop.goodies.infra.tests.UserTestConstants.USER_ID;
import static com.shop.goodies.infra.tests.UserTestConstants.USER_LAST_NAME;
import static com.shop.goodies.infra.tests.UserTestConstants.USER_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {


    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserService userService;


    private Category categoryTest;

    private User userTest;

    private ProductForm productForm;

    private Product product;

    private CategoryForm categoryForm;

    private Category categoryResult;
    private List<Category> categoriesExpected;

    private List<Category> categoriesResult;


    @Test
    void shouldReturnAllCategories() {

        givenCategory();
        givenCategories();
        whenCallingCategoryRepository_findAll();
        whenCalled_getAllCategories();
        thenReturnAllCategories();
    }


    @Test
    void shouldCreateCategory() {
        //given
        givenCategoryForm();

        //when
        whenCalledCreateCategory();

        //then
        thenCreateCategory();

    }

    private void whenCalledCreateCategory() {
        categoryResult = productService.createCategory(categoryForm);
    }

    private void thenCreateCategory() {
        assertEquals(categoryResult.getName(), PRODUCT_CATEGORY_NAME);
        verify(categoryRepository, times(1)).save(any());
    }


    @Test
    void shouldCreateProduct() {
        //given
        givenCategory();
        givenUser();
        givenProductForm();

        //when
        whenCallingUserService_findById();
        whenCallingCategoryRepository_findById();
        whenCalledCreateProduct();

        //then
        thenCreateProduct();

    }

    private void thenCreateProduct() {
        assertEquals(product.getName(), PRODUCT_NAME);
        verify(productRepository, times(1)).save(any());
    }

    private void whenCalledCreateProduct() {
        product = productService.createProduct(USER_ID, productForm);
    }

    private void whenCallingCategoryRepository_findById() {
        when(categoryRepository.findById(PRODUCT_CATEGORY_ID)).thenReturn(Optional.of(categoryTest));
    }

    private void whenCallingUserService_findById() {
        when(userService.findById(USER_ID)).thenReturn(Optional.of(userTest));
    }

    private void givenProductForm() {
        productForm = ProductForm.builder()
                .name(PRODUCT_NAME)
                .price(PRODUCT_PRICE)
                .availableStock(10)
                .categoryId(PRODUCT_CATEGORY_ID)
                .build();
    }

    private void givenUser() {
        userTest = User.builder().email(USER_EMAIL)
                .id(USER_ID)
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .password(USER_PASSWORD)
                .build();
    }

    private void givenCategory() {
        categoryTest = Category
                .builder()
                .id(PRODUCT_CATEGORY_ID)
                .name(PRODUCT_CATEGORY_NAME)
                .build();
    }

    private void givenCategoryForm() {
        categoryForm = CategoryForm.builder()
                .name(PRODUCT_CATEGORY_NAME)
                .build();
    }

    private void thenReturnAllCategories() {
        assertThat(categoriesResult).isEqualTo(categoriesExpected);
    }

    private void whenCalled_getAllCategories() {
        categoriesResult = productService.getAllCategories();

    }

    private void whenCallingCategoryRepository_findAll() {
        when(categoryRepository.findAll()).thenReturn(categoriesExpected);
    }

    private void givenCategories() {
        categoriesExpected = List.of(categoryTest);
    }

}