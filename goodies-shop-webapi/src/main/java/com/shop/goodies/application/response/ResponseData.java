package com.shop.goodies.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

@Getter
@AllArgsConstructor
public class ResponseData<T> extends RepresentationModel<ResponseData<T>> {

    private T body;


    public static <T> ResponseData<T> of(T body, Link... links) {

        ResponseData<T> responseData = new ResponseData<>(body);

        if (links.length > 0) {
            for (Link link : links) {
                responseData.add(link);
            }
        }

        return responseData;
    }

}
