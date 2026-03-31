package com.kiranaos.kiranaos_auth_service.repository;

import com.kiranaos.kiranaos_auth_service.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
}
