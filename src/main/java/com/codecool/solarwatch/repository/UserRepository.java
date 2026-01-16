package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByUsernameIgnoreCase(String username);
}
