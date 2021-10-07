package com.shop.goodies.domain.product;

import com.shop.goodies.common.DomainException;
import com.shop.goodies.domain.user.User;
import com.shop.goodies.domain.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final UserService userService;


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

    @Override
    public Product createProduct(Long sellerId, ProductForm productForm) {

        User seller = userService.findById(sellerId).orElseThrow(() -> new DomainException("User not found"));

        Category category = categoryRepository.findById(productForm.getCategoryId()).
                orElseThrow(() -> new DomainException("Category not found"));

        Product product = Product.builder()
                .seller(seller)
                .category(category)
                .name(productForm.getName())
                .availableStock(productForm.getAvailableStock())
                .price(productForm.getPrice())
                .build();

        productRepository.save(product);

        return product;

    }
}
