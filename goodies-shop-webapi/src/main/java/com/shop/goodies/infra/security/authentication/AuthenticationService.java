package com.shop.goodies.infra.security.authentication;

import com.shop.goodies.application.request.CredentialRequest;


public interface AuthenticationService {

    Token authenticate(CredentialRequest request);

}
