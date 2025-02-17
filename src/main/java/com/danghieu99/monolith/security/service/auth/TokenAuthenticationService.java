package com.danghieu99.monolith.security.service.auth;

import com.danghieu99.monolith.security.config.auth.AuthTokenProperties;
import com.danghieu99.monolith.security.config.auth.UserDetailsImpl;
import com.danghieu99.monolith.security.entity.Account;
import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.security.service.dao.AccountService;
import com.danghieu99.monolith.security.util.TokenUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenAuthenticationService {

    private final AuthTokenProperties authTokenProperties;
    private final AccountService accountService;
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    public String buildAccessToken(UserDetailsImpl userDetails) {
        SecretKey secretKey = Keys.hmacShaKeyFor(authTokenProperties.getTokenSecretKey().getBytes());
        Claims claims = Jwts.claims()
                .subject(String.valueOf(userDetails.getId()))
                .issuer(authTokenProperties.getTokenIssuer())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(authTokenProperties.getAccessTokenExpireMs())))
                .build();
        return TokenUtil.buildToken(secretKey, claims);
    }

    public String buildRefreshToken(UserDetailsImpl userDetails) {
        SecretKey secretKey = Keys.hmacShaKeyFor(authTokenProperties.getTokenSecretKey().getBytes());
        Claims claims = Jwts.claims()
                .subject(String.valueOf(userDetails.getId()))
                .issuer(authTokenProperties.getTokenIssuer())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(authTokenProperties.getRefreshTokenExpireMs())))
                .build();
        return TokenUtil.buildToken(secretKey, claims);
    }

    public Claims parseClaimsFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(authTokenProperties.getTokenSecretKey().getBytes());
        return TokenUtil.parseToken(secretKey, authTokenProperties.getTokenIssuer(), token);
    }

    public boolean isTokenStored(String refreshToken) {
        try {
            tokenService.findByValue(refreshToken)
                    .orElseThrow(() -> new ResourceNotFoundException("Token", "Value", refreshToken));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public UserDetailsImpl getUserDetailsFromToken(String refreshToken) {
        Account account = accountService.getById(Integer.valueOf(parseClaimsFromToken(refreshToken).getSubject()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(account.getUsername());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    public void deleteCurrentRefreshToken(HttpServletRequest request) {
        String refresh = TokenUtil.parseTokenFromCookies(request.getCookies(), authTokenProperties.getRefreshTokenName());
        refreshTokenService.deleteByValue(refresh);
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaimsFromToken(token);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }
}