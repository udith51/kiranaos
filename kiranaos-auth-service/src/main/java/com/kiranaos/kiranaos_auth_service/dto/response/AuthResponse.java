package com.kiranaos.kiranaos_auth_service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class AuthResponse {
    private UUID userId;
    private String accessToken;
    private String refreshToken;
    private final String tokenType = "Bearer";
}
