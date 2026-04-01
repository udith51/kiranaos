package com.kiranaos.kiranaos_auth_service.service;

import com.kiranaos.kiranaos_auth_service.config.JwtProperties;
import com.kiranaos.kiranaos_auth_service.domain.RefreshToken;
import com.kiranaos.kiranaos_auth_service.domain.User;
import com.kiranaos.kiranaos_auth_service.dto.request.LoginRequest;
import com.kiranaos.kiranaos_auth_service.dto.request.RefreshTokenRequest;
import com.kiranaos.kiranaos_auth_service.dto.request.RegisterRequest;
import com.kiranaos.kiranaos_auth_service.dto.response.AuthResponse;
import com.kiranaos.kiranaos_auth_service.dto.response.MessageResponse;
import com.kiranaos.kiranaos_auth_service.exception.EmailAlreadyExistsException;
import com.kiranaos.kiranaos_auth_service.exception.InvalidCredentialsException;
import com.kiranaos.kiranaos_auth_service.exception.InvalidTokenException;
import com.kiranaos.kiranaos_auth_service.repository.RefreshTokenRepository;
import com.kiranaos.kiranaos_auth_service.repository.UserRepository;
import com.kiranaos.kiranaos_auth_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    public MessageResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        return new MessageResponse("Registration successful");
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return generateTokenPair(user);
    }

    public AuthResponse generateTokenPair(User user) {
        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String rawRefreshToken = jwtService.generateRefreshToken();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setTokenHash(jwtService.hashToken(rawRefreshToken));
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtProperties.getRefreshTokenExpiryMs()));
        refreshToken.setRevoked(false);
        refreshTokenRepository.save(refreshToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(rawRefreshToken)
                .build();
    }

    public AuthResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        String tokenHash = jwtService.hashToken(refreshTokenRequest.getRefreshToken());
        RefreshToken stored = refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));
        if (stored.getRevoked()) {
            throw new InvalidTokenException("Refresh token revoked");
        }
        if (stored.getExpiryDate().isBefore(Instant.now())) {
            throw new InvalidTokenException("Refresh token expired");
        }
        stored.setRevoked(true);
        refreshTokenRepository.save(stored);
        return generateTokenPair(stored.getUser());
    }

    public MessageResponse logout(RefreshTokenRequest refreshTokenRequest) {
        String tokenHash = jwtService.hashToken(refreshTokenRequest.getRefreshToken());
        refreshTokenRepository.findByTokenHash(tokenHash)
                .ifPresent(refreshToken -> {
                    refreshToken.setRevoked(true);
                    refreshTokenRepository.save(refreshToken);
                });
        return new MessageResponse("Logged out successfully");
    }

}
