package com.codecool.solarwatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SolarEventsDTO(String city, LocalDate date, String sunrise, String sunset, String timezone) {
}
