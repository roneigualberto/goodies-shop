package com.shop.goodies.application.links;

import com.shop.goodies.application.controller.ProductController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ProductLinks extends BaseLinks {


    private static final Class<ProductController> controllerClass = ProductController.class;

    public static Link getCategories(String rel) {
        WebMvcLinkBuilder linkTo = linkTo(methodOn(controllerClass).getCategories());
        return linkToRel(rel, linkTo);
    }

    public static Link createCategory(String rel) {
        WebMvcLinkBuilder linkTo = linkTo(methodOn(controllerClass).createCategory(null));
        return linkToRel(rel, linkTo);
    }

    public static Link createProduct(String rel) {
        WebMvcLinkBuilder linkTo = linkTo(methodOn(controllerClass).createProduct(null));
        return linkToRel(rel, linkTo);
    }


}
