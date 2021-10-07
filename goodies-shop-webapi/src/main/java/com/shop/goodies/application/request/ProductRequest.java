package com.shop.goodies.application.request;


import com.shop.goodies.domain.product.ProductForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductRequest {

    private String name;

    private Long categoryId;

    private Double price;

    private Integer availableStock;

    public ProductForm toProductForm() {
        return new ProductForm(name, categoryId, price, availableStock);
    }
}
