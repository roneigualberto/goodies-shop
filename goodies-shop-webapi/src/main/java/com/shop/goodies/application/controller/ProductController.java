package com.shop.goodies.application.controller;


import com.shop.goodies.application.response.CategoryResponse;
import com.shop.goodies.domain.product.Category;
import com.shop.goodies.domain.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
