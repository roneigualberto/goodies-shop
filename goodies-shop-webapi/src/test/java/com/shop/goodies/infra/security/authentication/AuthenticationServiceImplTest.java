package com.shop.goodies.infra.security.authentication;

import com.shop.goodies.application.request.CredentialRequest;
import com.shop.goodies.domain.user.User;
import com.shop.goodies.infra.tests.UserTestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static com.shop.goodies.infra.tests.UserTestConstants.USER_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {


    @InjectMocks
    private AuthenticationServiceImpl authenticationService;


    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @Mock
    private TokenService tokenService;


    @Test
    public void getToken() {
        CredentialRequest request = CredentialRequest.builder().password(USER_PASSWORD).username(UserTestConstants.USER_EMAIL).build();

        User user = User.builder().email(UserTestConstants.USER_EMAIL).build();

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(user);

        Token tokenExpected = Token.builder()
                .accessToken("token-1223")
                .type("Bearer")
                .build();

        when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(authenticatedUser);
        when(tokenService.generateToken(authenticatedUser)).thenReturn(tokenExpected);

        Token tokenResult = authenticationService.authenticate(request);

        assertThat(tokenResult).isEqualTo(tokenExpected);

    }

}