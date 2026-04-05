package com.kiranaos.kiranaos_auth_service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private final String tokenType = "Bearer";
}
