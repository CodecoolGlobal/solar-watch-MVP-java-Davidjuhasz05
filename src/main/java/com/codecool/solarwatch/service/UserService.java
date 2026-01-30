package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.UserExistsException;
import com.codecool.solarwatch.model.dto.JwtResponseDTO;
import com.codecool.solarwatch.model.dto.UserDTO;
import com.codecool.solarwatch.model.user.Role;
import com.codecool.solarwatch.model.user.SolarWatchUser;
import com.codecool.solarwatch.repository.UserRepository;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authManager;
  private final PasswordEncoder encoder;

  public void signUp(UserDTO user) {
    if (userRepository.findByUsernameIgnoreCase(user.username()).isPresent()) {
      throw new UserExistsException("A user already exists with this username: " + user.username());
    }
    String encodedPassword = encoder.encode(user.password());
    SolarWatchUser solarWatchUser = SolarWatchUser.builder()
            .username(user.username())
            .password(encodedPassword)
            .roles(Set.of(Role.ROLE_USER))
            .build();
    userRepository.save(solarWatchUser);
  }

  public JwtResponseDTO login(UserDTO user) {
    Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.username(), user.password()));
    SecurityContextHolder.getContext().setAuthentication(auth);
    String jwt = jwtUtils.generateJwtToken(auth);
    User userDetails = (User) auth.getPrincipal();
    Set<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    return new JwtResponseDTO(jwt, userDetails.getUsername(), roles);
  }

}
