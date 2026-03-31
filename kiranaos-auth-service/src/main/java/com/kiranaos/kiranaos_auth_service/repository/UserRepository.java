package com.kiranaos.kiranaos_auth_service.repository;

import com.kiranaos.kiranaos_auth_service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
