package com.shop.goodies.infra.security;

import com.shop.goodies.domain.user.HashGenerator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class BCryptHashGenerator implements HashGenerator {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String hash(String plainText) {
        return passwordEncoder.encode(plainText);
    }
}
