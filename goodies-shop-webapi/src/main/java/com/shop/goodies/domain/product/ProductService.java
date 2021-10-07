package com.shop.goodies.domain.product;

import java.util.List;

public interface ProductService {

    List<Category> getAllCategories();

    Category createCategory(CategoryForm categoryForm);
}
