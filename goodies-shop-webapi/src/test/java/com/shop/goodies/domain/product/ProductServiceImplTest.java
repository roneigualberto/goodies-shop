package com.shop.goodies.domain.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

}