package com.codecool.solarwatch.configuration;

import com.codecool.solarwatch.model.user.Role;
import com.codecool.solarwatch.model.user.SolarWatchUser;
import com.codecool.solarwatch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminUserSeeder implements CommandLineRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  @Value("${solarwatch.admin.username}")
  private String adminUsername;

  @Value("${solarwatch.admin.password}")
  private String adminPassword;

  @Override
  public void run(String... args) {
    if (!userRepository.existsByUsernameIgnoreCase(adminUsername)) {
      log.info("Admin user not found. Creating default admin user: '{}'", adminUsername);

      SolarWatchUser admin = SolarWatchUser.builder()
              .username(adminUsername)
              .password(encoder.encode(adminPassword))
              .roles(Set.of(Role.ROLE_USER, Role.ROLE_ADMIN))
              .build();

      userRepository.save(admin);
      log.info("Default admin user '{}' has been created.", adminUsername);
    } else {
      log.info("Admin user '{}' already exists. Skipping seeding.", adminUsername);
    }
  }
}
