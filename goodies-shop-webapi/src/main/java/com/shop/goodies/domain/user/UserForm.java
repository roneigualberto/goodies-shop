package com.shop.goodies.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserForm {

    private String email;

    private String firstName;

    private String lastName;

    private String password;
}