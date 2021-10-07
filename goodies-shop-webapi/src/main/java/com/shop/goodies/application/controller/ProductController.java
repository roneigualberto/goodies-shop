package com.shop.goodies.application.controller;


import com.shop.goodies.application.request.CategoryRequest;
import com.shop.goodies.application.request.ProductRequest;
import com.shop.goodies.application.response.CategoryResponse;
import com.shop.goodies.application.response.ProductResponse;
import com.shop.goodies.domain.product.Category;
import com.shop.goodies.domain.product.CategoryForm;
import com.shop.goodies.domain.product.Product;
import com.shop.goodies.domain.product.ProductForm;
import com.shop.goodies.domain.product.ProductService;
import com.shop.goodies.infra.security.authentication.AuthenticatedUser;
import com.shop.goodies.infra.security.authentication.AuthenticatedUserService;
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

    private final AuthenticatedUserService authenticatedUserService;


    @Transactional
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {

        ProductForm form = request.toProductForm();

        AuthenticatedUser autenticatedUser = authenticatedUserService.getAutenticatedUser();

        Long sellerId = autenticatedUser.getUser().getId();

        Product savedProduct = productService.createProduct(sellerId, form);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProduct.getId())
                .toUri();

        ProductResponse response = ProductResponse.fromProduct(savedProduct);

        return ResponseEntity.created(location).body(response);
    }

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
