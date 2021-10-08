package com.shop.goodies.application.response;

import com.shop.goodies.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductResponse  {

    private Long id;

    private String name;

    private CategoryResponse category;

    private UserResponse seller;

    public static ProductResponse fromProduct(Product product) {
        CategoryResponse category = CategoryResponse.fromCategory(product.getCategory());
        UserResponse seller = UserResponse.fromUser(product.getSeller());
        return new ProductResponse(product.getId(), product.getName(), category, seller);
    }

    public static List<ProductResponse> fromProducts(List<Product> products) {
        return products.stream().map((ProductResponse::fromProduct)).collect(Collectors.toList());
    }
}