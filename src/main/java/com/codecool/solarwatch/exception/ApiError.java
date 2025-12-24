package com.codecool.solarwatch.exception;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError {
  private LocalDateTime timestamp;
  private int status;
  private String error;
  private String message;
}
