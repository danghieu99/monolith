package com.danghieu99.monolith.auth.config.authentication;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;;

@ConfigurationProperties(prefix = "com.danghieu99.monolith.jwt")
@Getter
public class TokenProperties {

    @ConstructorBinding
    public TokenProperties(String cookiePath, String tokenIssuer, String tokenSecretKey, String accessTokenName, String refreshTokenName, int accessTokenExpireMs, int refreshTokenExpireMs) {
        this.cookiePath = cookiePath;
        this.tokenIssuer = tokenIssuer;
        this.tokenSecretKey = tokenSecretKey;
        this.accessTokenName = accessTokenName;
        this.refreshTokenName = refreshTokenName;
        this.accessTokenExpireMs = accessTokenExpireMs;
        this.refreshTokenExpireMs = refreshTokenExpireMs;
    }

    private final String cookiePath;

    private final String tokenIssuer;

    private final String tokenSecretKey;

    private final String accessTokenName;

    private final String refreshTokenName;

    private final int accessTokenExpireMs;

    private final int refreshTokenExpireMs;
}