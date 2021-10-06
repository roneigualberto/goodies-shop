package com.shop.goodies.application.response;

import com.shop.goodies.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponse {


    private Long id;

    private String email;

    private String firstName;

    private String lastName;


    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());

    }
}