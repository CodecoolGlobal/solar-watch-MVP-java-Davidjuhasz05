package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.user.SolarWatchUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SolarWatchUser, Long> {
  Optional<SolarWatchUser> findByUsernameIgnoreCase(String username);
}
