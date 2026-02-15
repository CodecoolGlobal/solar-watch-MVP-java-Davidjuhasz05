package com.codecool.solarwatch.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SolarEventResponse(Results results, String tzid) {

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record Results(String sunrise, String sunset) {}

}
