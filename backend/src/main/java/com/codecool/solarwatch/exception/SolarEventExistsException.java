package com.codecool.solarwatch.exception;

import java.time.LocalDate;

public class SolarEventExistsException extends RuntimeException {
  public SolarEventExistsException(String city, LocalDate date) {
    super(String.format("Solar event for city: '%s' on date '%s' already exists", city, date));
  }
}
