package com.shop.goodies.application.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CredentialRequest {

    private String username;

    private String password;
}
