package com.kiranaos.kiranaos_auth_service.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
@CommonsLog
@Setter @Getter
public class JwtProperties {
    private String secret;
    private long accessTokenExpiryMs;
    private long refreshTokenExpiryMs;
}
