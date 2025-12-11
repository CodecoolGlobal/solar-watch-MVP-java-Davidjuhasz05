package com.codecool.solarwatch.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException e) {
    log.warn(e.getMessage());

    ApiError error = new ApiError();
    error.setTimestamp(LocalDateTime.now());
    error.setStatus(HttpStatus.NOT_FOUND.value());
    error.setError(HttpStatus.NOT_FOUND.toString());
    error.setMessage(e.getMessage());

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiError> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
    log.warn("Parameter mismatch. Param: '{}', Value: '{}', Message: {}",
            e.getName(), e.getValue(), e.getMessage());

    ApiError error = new ApiError();
    error.setTimestamp(LocalDateTime.now());
    error.setStatus(HttpStatus.BAD_REQUEST.value());
    error.setError(HttpStatus.BAD_REQUEST.toString());

    if ("date".equals(e.getName())) {
      error.setMessage("Invalid Date. Please use YYYY-MM-DD format.");
      return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    error.setMessage(e.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ApiError> handleMissingRequestParam(MissingServletRequestParameterException e) {
    log.warn("Missing required parameter: '{}'", e.getParameterName());

    ApiError error = new ApiError();
    error.setTimestamp(LocalDateTime.now());
    error.setStatus(HttpStatus.BAD_REQUEST.value());
    error.setError(HttpStatus.BAD_REQUEST.toString());
    error.setMessage("Missing parameter: " + e.getParameterName());

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGlobalException(Exception e) {
    log.error("An unexpected internal server error occurred.", e);

    ApiError error = new ApiError();
    error.setTimestamp(LocalDateTime.now());
    error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    error.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
    error.setMessage("Something went wrong. Please try again.");

    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
