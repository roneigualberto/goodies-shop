package com.shop.goodies.infra.security.authentication;

import com.shop.goodies.domain.user.User;
import com.shop.goodies.domain.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AutenticatedUserServiceImpl implements AuthenticatedUserService {


    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByEmail(username);
    }

    private AuthenticatedUser getByEmail(String username) {
        Optional<User> optUser = userService.findByEmail(username);

        User user = optUser.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return new AuthenticatedUser(user);
    }

    @Override
    public AuthenticatedUser getAutenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(username);
    }
}
