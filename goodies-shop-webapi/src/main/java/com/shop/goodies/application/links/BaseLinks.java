package com.shop.goodies.application.links;

import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Getter
public class BaseLinks {

    public static final String SELF = "self";

    protected static Link linkToRel(String rel, WebMvcLinkBuilder linkTo) {
        if (SELF.equalsIgnoreCase(rel)) {
            return linkTo.withSelfRel();
        }
        return linkTo.withRel(rel);
    }
}
