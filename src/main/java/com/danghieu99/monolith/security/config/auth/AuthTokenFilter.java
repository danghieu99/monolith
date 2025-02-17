package com.danghieu99.monolith.security.config.auth;

import com.danghieu99.monolith.security.entity.Account;
import com.danghieu99.monolith.security.service.dao.AccountService;
import com.danghieu99.monolith.security.service.auth.TokenAuthenticationService;
import com.danghieu99.monolith.security.util.TokenUtil;
import com.danghieu99.monolith.security.service.auth.UserDetailsServiceImpl;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
@RequiredArgsConstructor
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    TokenAuthenticationService tokenAuthenticationService;
    UserDetailsServiceImpl userDetailsService;
    AuthTokenProperties authTokenProperties;
    AccountService accountService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String access = TokenUtil.parseTokenFromCookies(request.getCookies(), authTokenProperties.getAccessTokenName());
            if (access != null && !access.isEmpty()
                    && tokenAuthenticationService.isTokenValid(access)) {
                Integer userId = Integer.valueOf(tokenAuthenticationService.parseClaimsFromToken(access).getSubject());
                Account account = accountService.getById(userId);
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
            } else {
                String refresh = TokenUtil.parseTokenFromCookies(request.getCookies(), authTokenProperties.getRefreshTokenName());
                if (refresh != null && !refresh.isEmpty()
                        && tokenAuthenticationService.isTokenValid(refresh)
                        && tokenAuthenticationService.isTokenStored(refresh)) {
                    UserDetailsImpl userDetails = tokenAuthenticationService.getUserDetailsFromToken(refresh);
                    String newAccessToken = tokenAuthenticationService.buildAccessToken(userDetails);
                    ResponseCookie cookie = ResponseCookie.from(authTokenProperties.getAccessTokenName(), newAccessToken)
                            .httpOnly(true)
                            .secure(true)
                            .path(authTokenProperties.getCookiePath())
                            .build();
                    response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
                }
            }
        } catch (JwtException e) {
            log.error("Unexpected token exception: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}