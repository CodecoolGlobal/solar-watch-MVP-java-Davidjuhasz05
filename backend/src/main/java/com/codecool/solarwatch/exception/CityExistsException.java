package com.codecool.solarwatch.exception;

public class CityExistsException extends RuntimeException {
  public CityExistsException(String name) {
    super("City with name '" + name + "' already exists");
  }
}
