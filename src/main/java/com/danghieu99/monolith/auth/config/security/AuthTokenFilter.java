package com.danghieu99.monolith.auth.config.security;

import com.danghieu99.monolith.auth.config.authentication.TokenProperties;
import com.danghieu99.monolith.auth.config.authentication.UserDetailsImpl;
import com.danghieu99.monolith.auth.entity.Account;
import com.danghieu99.monolith.auth.service.account.AccountCrudService;
import com.danghieu99.monolith.auth.service.auth.TokenAuthenticationService;
import com.danghieu99.monolith.auth.service.auth.TokenUtil;
import com.danghieu99.monolith.auth.service.auth.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    TokenProperties tokenProperties;

    @Autowired
    AccountCrudService accountCrudService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String access = TokenUtil.parseTokenFromCookies(request.getCookies(), tokenProperties.getAccessTokenName());
            if (access != null && !access.isEmpty() && tokenAuthenticationService.isTokenValid(access) && !tokenAuthenticationService.isTokenExpired(access)) {
                Integer userId = Integer.valueOf(tokenAuthenticationService.parseClaimsFromToken(access).getSubject());
                Account account = accountCrudService.getById(userId);
                UserDetails userDetails = userDetailsService.loadUserByUsername(account.getUsername());
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            if (access != null && !access.isEmpty() && tokenAuthenticationService.isTokenValid(access) && tokenAuthenticationService.isTokenExpired(access)) {
                String refresh = TokenUtil.parseTokenFromCookies(request.getCookies(), tokenProperties.getRefreshTokenName());
                if (refresh != null && !refresh.isEmpty() && tokenAuthenticationService.isTokenValid(refresh)
                        && tokenAuthenticationService.validateTokenSubjects(access, refresh)
                        && tokenAuthenticationService.isTokenStored(refresh)) {
                    UserDetailsImpl userDetails = tokenAuthenticationService.getUserDetailsFromToken(refresh);
                    String newAccessToken = tokenAuthenticationService.buildAccessToken(userDetails);
                    ResponseCookie cookie = ResponseCookie.from(tokenProperties.getAccessTokenName(), newAccessToken)
                            .httpOnly(true)
                            .secure(true)
                            .path(tokenProperties.getCookiePath())
                            .build();
                    response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.debug("Unexpected token error: {}", e.getMessage());
        }
    }
}