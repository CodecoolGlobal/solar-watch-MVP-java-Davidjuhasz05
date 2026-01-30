package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.dto.JwtResponseDTO;
import com.codecool.solarwatch.model.dto.UserDTO;
import com.codecool.solarwatch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<Void> signUp(@RequestBody UserDTO user) {
    userService.signUp(user);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/login")
  public JwtResponseDTO login(@RequestBody UserDTO user) {
    return userService.login(user);
  }

}
