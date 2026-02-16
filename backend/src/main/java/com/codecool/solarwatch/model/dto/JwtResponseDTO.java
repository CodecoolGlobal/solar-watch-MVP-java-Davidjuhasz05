package com.codecool.solarwatch.model.dto;

import java.util.Set;

public record JwtResponseDTO(String token, String username, Set<String> roles) {
}
