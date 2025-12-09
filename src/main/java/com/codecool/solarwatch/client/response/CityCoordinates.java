package com.codecool.solarwatch.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CityCoordinates(double lat, double lon) {
}
