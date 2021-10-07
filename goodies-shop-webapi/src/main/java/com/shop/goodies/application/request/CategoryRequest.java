package com.shop.goodies.application.request;


import com.shop.goodies.domain.product.CategoryForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CategoryRequest {

    private String name;

    public CategoryForm toCategoryForm() {
        return new CategoryForm(name);
    }
}
