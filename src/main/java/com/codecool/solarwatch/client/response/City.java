package com.codecool.solarwatch.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record City(String name, double lat, double lon, String country, String state) {
}
