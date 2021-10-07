package com.shop.goodies.infra.security;


import com.shop.goodies.domain.user.User;
import com.shop.goodies.domain.user.UserService;
import com.shop.goodies.infra.security.authentication.AutenticatedUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static com.shop.goodies.infra.tests.UserTestConstants.HASH_PASSWORD;
import static com.shop.goodies.infra.tests.UserTestConstants.USER_EMAIL;
import static com.shop.goodies.infra.tests.UserTestConstants.USER_FIRST_NAME;
import static com.shop.goodies.infra.tests.UserTestConstants.USER_LAST_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticatedUserServiceTest {


    @InjectMocks
    private AutenticatedUserServiceImpl autenticatedUserService;

    @Mock
    private UserService userService;

    @Test
    public void shouldNotReturnUserIdentity_userNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> autenticatedUserService.loadUserByUsername(USER_EMAIL));
    }

    @Test
    public void shouldReturnUserIdentity_userFound() {
        User existentUser = User.builder()
                .email(USER_EMAIL)
                .password(HASH_PASSWORD)
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .email(USER_EMAIL)
                .build();

        when(userService.findByEmail(USER_EMAIL)).thenReturn(Optional.of(existentUser));

        UserDetails userDetails = autenticatedUserService.loadUserByUsername(USER_EMAIL);

        assertEquals(userDetails.getUsername(), USER_EMAIL);
        assertEquals(userDetails.getPassword(), HASH_PASSWORD);


    }

}