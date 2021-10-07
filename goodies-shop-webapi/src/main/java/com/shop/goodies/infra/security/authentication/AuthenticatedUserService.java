package com.shop.goodies.infra.security.authentication;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticatedUserService extends UserDetailsService {


    AuthenticatedUser getByEmail(String email);
}
