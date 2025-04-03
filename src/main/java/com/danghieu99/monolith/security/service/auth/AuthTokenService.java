package com.danghieu99.monolith.security.service.auth;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.security.config.auth.AuthTokenProperties;
import com.danghieu99.monolith.security.config.auth.UserDetailsImpl;
import com.danghieu99.monolith.security.entity.Account;
import com.danghieu99.monolith.security.repository.jpa.AccountRepository;
import com.danghieu99.monolith.security.util.TokenUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthTokenService {

    private final AccountRepository accountRepository;
    private final AuthTokenProperties authTokenProperties;
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenService refreshTokenService;

    public ResponseCookie buildRefreshTokenCookie(@NotNull UserDetailsImpl userDetails) {
        return ResponseCookie
                .from(authTokenProperties.getRefreshTokenName(), buildRefreshToken(userDetails))
                .secure(true)
                .httpOnly(true)
                .maxAge(TimeUnit.MILLISECONDS.toSeconds(authTokenProperties.getRefreshTokenExpireMs()))
                .build();
    }

    public ResponseCookie buildAccessTokenCookie(@NotNull UserDetailsImpl userDetails) {
        return ResponseCookie
                .from(authTokenProperties.getAccessTokenName(), buildAccessToken(userDetails))
                .secure(true)
                .httpOnly(true)
                .maxAge(TimeUnit.MILLISECONDS.toSeconds(authTokenProperties.getAccessTokenExpireMs()))
                .build();
    }

    public String buildAccessToken(UserDetailsImpl userDetails) {
        SecretKey secretKey = Keys.hmacShaKeyFor(authTokenProperties.getTokenSecretKey().getBytes());
        Claims claims = Jwts.claims()
                .subject(String.valueOf(userDetails.getUuid()))
                .issuer(authTokenProperties.getTokenIssuer())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(authTokenProperties.getAccessTokenExpireMs())))
                .build();
        return TokenUtil.buildToken(secretKey, claims);
    }

    public String buildRefreshToken(UserDetailsImpl userDetails) {
        SecretKey secretKey = Keys.hmacShaKeyFor(authTokenProperties.getTokenSecretKey().getBytes());
        Claims claims = Jwts.claims()
                .subject(String.valueOf(userDetails.getUuid()))
                .issuer(authTokenProperties.getTokenIssuer())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(authTokenProperties.getRefreshTokenExpireMs())))
                .build();
        return TokenUtil.buildToken(secretKey, claims);
    }

    public Claims parseClaimsFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(authTokenProperties.getTokenSecretKey().getBytes());
        return TokenUtil.parseClaimsFromToken(secretKey, authTokenProperties.getTokenIssuer(), token);
    }

    public boolean isTokenStored(String refreshToken) {
        return refreshTokenService.existsByValue(refreshToken);
    }

    public UserDetailsImpl getUserDetailsFromToken(String refreshToken) {
        String uuid = parseClaimsFromToken(refreshToken).getSubject();
        Account account = accountRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new ResourceNotFoundException("Account", "Id", uuid));
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