package com.danghieu99.monolith.auth.config.security;

import com.danghieu99.monolith.auth.config.authentication.TokenProperties;
import com.danghieu99.monolith.auth.config.authentication.UserDetailsImpl;
import com.danghieu99.monolith.auth.entity.Account;
import com.danghieu99.monolith.auth.service.account.AccountCrudService;
import com.danghieu99.monolith.auth.service.token.AuthTokenService;
import com.danghieu99.monolith.auth.util.TokenUtil;
import com.danghieu99.monolith.auth.service.auth.UserDetailsServiceImpl;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@Slf4j
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    AuthTokenService authTokenService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    TokenProperties tokenProperties;

    @Autowired
    AccountCrudService accountCrudService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String access = TokenUtil.parseTokenFromCookies(request.getCookies(), tokenProperties.getAccessTokenName());

            if (access != null && !access.isEmpty()) {
                if (authTokenService.isTokenValid(access)) {
                    Integer userId = Integer.valueOf(authTokenService.parseClaimsFromToken(access).getSubject());
                    Account account = accountCrudService.getById(userId);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(account.getUsername());
                    try {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails,
                                        null,
                                        userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } catch (AuthenticationException e) {
                        log.error("Authentication exception: {}", e.getMessage());
                        throw e;
                    }
                }
                if (authTokenService.isTokenCryptValid(access) && authTokenService.isTokenExpired(access)) {
                    String refresh = TokenUtil.parseTokenFromCookies(request.getCookies(), tokenProperties.getRefreshTokenName());
                    if (refresh != null && !refresh.isEmpty() && authTokenService.isTokenValid(refresh)
                            && authTokenService.validateTokenSubjects(access, refresh)
                            && authTokenService.isTokenStored(refresh)) {
                        UserDetailsImpl userDetails = authTokenService.getUserDetailsFromToken(refresh);
                        String newAccessToken = authTokenService.buildAccessToken(userDetails);
                        ResponseCookie cookie = ResponseCookie.from(tokenProperties.getAccessTokenName(), newAccessToken)
                                .httpOnly(true)
                                .secure(true)
                                .path(tokenProperties.getCookiePath())
                                .build();
                        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
                    }
                }
            }
        } catch (JwtException e) {
            log.error("Unexpected token exception: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}