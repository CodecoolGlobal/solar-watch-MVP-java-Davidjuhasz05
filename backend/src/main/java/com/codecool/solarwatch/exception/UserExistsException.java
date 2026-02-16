package com.codecool.solarwatch.exception;

public class UserExistsException extends RuntimeException {
  public UserExistsException(String name) {
    super("User with name '" + name + "' already exists");
  }
}
