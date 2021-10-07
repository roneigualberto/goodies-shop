package com.shop.goodies.infra.security.authentication;

import com.shop.goodies.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.shop.goodies.infra.security.constants.SecurityConstants.BEARER_TOKEN;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {


    private final JwtConfig jwtConfig;


    @Override
    public Token generateToken(AuthenticatedUser autenticated) {

        User user = autenticated.getUser();

        Date today = new Date();

        Date expirationDate = new Date(today.getTime() + Long.parseLong(jwtConfig.getAccessTokenExpiration()));

        String accessToken = Jwts.builder()
                .setIssuer("Goodies Shop Application")
                .setSubject(user.getEmail())
                .setIssuedAt(today)
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret()).compact();

        return Token.builder().type(BEARER_TOKEN)
                .accessToken(accessToken)
                .build();
    }


    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
