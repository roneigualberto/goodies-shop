package com.shop.goodies.infra.security.authentication;

import com.shop.goodies.application.request.CredentialRequest;
import com.shop.goodies.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {


    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    @Override
    public Token authenticate(CredentialRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        AuthenticatedUser authenticatedUSer = (AuthenticatedUser) authentication.getPrincipal();
        return tokenService.generateToken(authenticatedUSer);
    }

}
