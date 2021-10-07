package com.shop.goodies.application.controller;


import com.shop.goodies.application.request.CategoryRequest;
import com.shop.goodies.application.response.CategoryResponse;
import com.shop.goodies.domain.product.Category;
import com.shop.goodies.domain.product.CategoryForm;
import com.shop.goodies.domain.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getCategories() {

        List<Category> categories = productService.getAllCategories();

        List<CategoryResponse> responseList = CategoryResponse.fromCategories(categories);

        return ResponseEntity.ok(responseList);
    }

    @Transactional
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {

        CategoryForm form = request.toCategoryForm();

        Category savedCategory = productService.createCategory(form);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCategory.getId())
                .toUri();

        CategoryResponse response = CategoryResponse.fromCategory(savedCategory);

        return ResponseEntity.created(location).body(response);
    }
}
