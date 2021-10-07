package com.shop.goodies.application.response;

import com.shop.goodies.domain.product.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryResponse {

    private Long id;

    private String name;

    public static CategoryResponse fromCategory(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }

    public static List<CategoryResponse> fromCategories(List<Category> categories) {
        return categories.stream().map((CategoryResponse::fromCategory)).collect(Collectors.toList());
    }
}