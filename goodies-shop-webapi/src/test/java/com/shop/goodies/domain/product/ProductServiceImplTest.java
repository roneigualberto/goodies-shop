package com.shop.goodies.domain.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {


    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private CategoryRepository categoryRepository;


    @Test
    void shouldReturnAllCategories() {

        Category trufa = Category.builder().id(1L)
                .name("Trufas")
                .build();

        Category empada = Category.builder().id(2L)
                .name("Empada")
                .build();

        Category boloDePote = Category.builder().id(2L)
                .name("Bolo de Pote")
                .build();

        List<Category> categoriesExpected = List.of(trufa, empada, boloDePote);

        Mockito.when(categoryRepository.findAll()).thenReturn(categoriesExpected);
        List<Category> categories = productService.getAllCategories();

        assertThat(categories).isEqualTo(categoriesExpected);
    }

    @Test
    void shouldCreateCategory() {
        //given
        CategoryForm trufa = CategoryForm.builder()
                .name("Trufa")
                .build();

        //when
        Category category = productService.createCategory(trufa);

        //then
        assertEquals(category.getName(), "Trufa");
        verify(categoryRepository, times(1)).save(any());

    }

}