package com.codecool.solarwatch.model.dto;

import com.codecool.solarwatch.model.user.Role;

import java.util.Set;

public record JwtResponseDTO(String token, String username, Set<String> roles) {
}
