package com.shop.goodies.domain.product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ProductForm {

    private String name;

    private Long categoryId;

    private Double price;

    private Integer availableStock;
}
