package com.shop.goodies.infra.security.authentication;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.shop.goodies.infra.security.constants.SecurityConstants.AUTHORIZATION_HEADER;
import static com.shop.goodies.infra.security.constants.SecurityConstants.BEARER_TOKEN;

@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticatedUserService authenticatedUserService;

    private final TokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getToken(request);
        boolean validToken = tokenService.isValidToken(token);

        if (validToken) {
            String username = tokenService.getUsername(token);
            UserDetails user = authenticatedUserService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (token == null || !token.startsWith(BEARER_TOKEN)) {
            return null;
        }
        return token.substring(7);
    }
}
