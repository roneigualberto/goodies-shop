package com.shop.goodies.domain.user;

import java.util.Optional;

public interface UserService {


    User createUser(UserForm user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);

}
