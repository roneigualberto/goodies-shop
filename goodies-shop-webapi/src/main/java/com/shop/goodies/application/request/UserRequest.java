package com.shop.goodies.application.request;


import com.shop.goodies.domain.user.UserForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserRequest {

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    public UserForm toUserForm() {
        return new UserForm(email, firstName, lastName, password);
    }
}
