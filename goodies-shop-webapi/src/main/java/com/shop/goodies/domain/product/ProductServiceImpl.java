package com.shop.goodies.domain.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(CategoryForm categoryForm) {

        Category category = Category.builder().name(categoryForm.getName()).build();

        categoryRepository.save(category);


        return category;
    }
}
