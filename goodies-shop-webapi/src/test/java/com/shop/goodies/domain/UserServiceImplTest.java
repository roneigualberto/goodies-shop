package com.shop.goodies.domain;

import com.shop.goodies.common.DomainException;
import com.shop.goodies.domain.user.HashGenerator;
import com.shop.goodies.domain.user.User;
import com.shop.goodies.domain.user.UserForm;
import com.shop.goodies.domain.user.UserRepository;
import com.shop.goodies.domain.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {


    private static final String USER_EMAIL = "user@gmail.com";
    private static final String USER_FIRST_NAME = "First Name";
    private static final String USER_LAST_NAME = "Last Name";
    private static final String USER_PASSWORD = "123";
    private static final String HASH_PASSWORD = "$2a$10$yYMOzoHo7G6E.mxUf32APOEk3D190pSVGdFwBOhe6ESmf1gImcqD6";


    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HashGenerator hashGenerator;


    @Test
    void shouldCreateUser() {
        //given
        UserForm userForm = UserForm.builder().email(USER_EMAIL)
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .password(USER_PASSWORD)
                .build();

        when(hashGenerator.hash(any())).thenReturn(HASH_PASSWORD);

        //
        User user = userService.createUser(userForm);


        assertEquals(user.getEmail(), USER_EMAIL);
        assertEquals(user.getPassword(), HASH_PASSWORD);
        assertEquals(user.getFirstName(), USER_FIRST_NAME);
        assertEquals(user.getLastName(), USER_LAST_NAME);
        verify(userRepository, times(1)).save(any());

    }

    @Test
    void shouldNotCreateUser_ifUserAlreadyExists() {
        //given
        UserForm userForm = UserForm.builder().email(USER_EMAIL)
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .password(USER_PASSWORD)
                .build();

        User existentUser = User.builder()
                .email(USER_EMAIL)
                .build();

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(existentUser));

        assertThrows(DomainException.class, () -> userService.createUser(userForm));

    }


}
