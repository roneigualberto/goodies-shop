package com.shop.goodies.application.controller;

import com.shop.goodies.application.request.CredentialRequest;
import com.shop.goodies.infra.security.authentication.AuthenticationService;
import com.shop.goodies.infra.security.authentication.Token;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {


    private final AuthenticationService authenticationService;

    @PostMapping("/token")
    public ResponseEntity<Token> authenticate(@RequestBody @Valid CredentialRequest request) {

        try {
            Token token = authenticationService.authenticate(request);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
