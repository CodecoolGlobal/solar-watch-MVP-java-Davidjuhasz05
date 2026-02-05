package com.codecool.solarwatch.model.dto;

import java.time.LocalDate;

public record CreateSolarEventsDTO(
        String city,
        String country,
        String state,
        LocalDate date,
        String sunrise,
        String sunset,
        String timezone
) {}
