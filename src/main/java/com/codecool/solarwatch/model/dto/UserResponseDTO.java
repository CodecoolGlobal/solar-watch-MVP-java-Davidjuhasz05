package com.codecool.solarwatch.model.dto;

import java.util.Set;

public record UserResponseDTO(Long id, String username, Set<String> roles) {
}
