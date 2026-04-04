package com.kiranaos.kiranaos_auth_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("jwt")
@Component
@Setter @Getter
public class JwtProperties {
    private String secret;
    private long accessTokenExpiryMs;
    private long refreshTokenExpiryMs;
}
