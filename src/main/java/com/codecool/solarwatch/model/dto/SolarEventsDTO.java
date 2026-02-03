package com.codecool.solarwatch.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SolarEventsDTO(String city, String country, String state, LocalDate date, String sunrise, String sunset, String timezone) {
}
