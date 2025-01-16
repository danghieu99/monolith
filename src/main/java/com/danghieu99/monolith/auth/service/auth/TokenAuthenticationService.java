package com.danghieu99.monolith.auth.service.auth;

import com.danghieu99.monolith.auth.config.authentication.TokenProperties;
import com.danghieu99.monolith.auth.config.authentication.UserDetailsImpl;
import com.danghieu99.monolith.auth.entity.Account;
import com.danghieu99.monolith.auth.exception.ResourceNotFoundException;
import com.danghieu99.monolith.auth.service.account.AccountService;
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

    private final TokenProperties tokenProperties;

    private final AccountService accountService;

    private final UserDetailsServiceImpl userDetailsService;

    private final RefreshTokenService tokenService;

    public void deleteRefreshTokenByValue(HttpServletRequest request) {
        String refresh = TokenUtil.parseTokenFromCookies(request.getCookies(), tokenProperties.getRefreshTokenName());
        tokenService.deleteByValue(refresh);
    }

    public void deleteRefreshTokenByUserId(int userId) {
        tokenService.deleteByUserId(userId);
    }

    public String buildAccessToken(UserDetailsImpl userDetails) {
        return buildToken(userDetails, tokenProperties.getAccessTokenExpireMs());
    }

    public String buildRefreshToken(UserDetailsImpl userDetails) {
        return buildToken(userDetails, tokenProperties.getRefreshTokenExpireMs());
    }

    public String buildToken(UserDetailsImpl userDetails, int expireIn) {
        SecretKey secretKey = Keys.hmacShaKeyFor(tokenProperties.getTokenSecretKey().getBytes());
        Claims claims = Jwts.claims()
                .subject(String.valueOf(userDetails.getId()))
                .issuer(tokenProperties.getTokenIssuer())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(expireIn)))
                .build();
        return TokenUtil.buildToken(secretKey, claims);
    }

    public Claims parseClaimsFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(tokenProperties.getTokenSecretKey().getBytes());
        return TokenUtil.parseToken(secretKey, tokenProperties.getTokenIssuer(), token);
    }

    public boolean validateTokenSubjects(String access, String refresh) {
        return parseClaimsFromToken(refresh).getSubject().trim().equals(parseClaimsFromToken(access).getSubject().trim());
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

    public boolean isTokenValid(String token) {
        try {
            parseClaimsFromToken(token);
        } catch (SecurityException | IncorrectClaimException | MalformedJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public boolean isTokenExpired(String token) {
        try {
            parseClaimsFromToken(token);
        } catch (ExpiredJwtException e) {
            return true;
        }
        return false;
    }
}