package com.codecool.solarwatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SolarEventsDTO(String city, String sunrise, String sunset, String timezone) {
}
