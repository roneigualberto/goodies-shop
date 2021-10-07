package com.shop.goodies.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CategoryForm {

    private String name;
}
