package com.bitesync.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bitesync.api.exception.EntityNotFoundException;
import com.bitesync.api.exception.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Object> handleResourceNotFoundException(RuntimeException ex) {
    ErrorResponse error = new ErrorResponse(Arrays.asList(ex.getMessage()));
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ResponseEntity<Object> handleDataAccessException(EmptyResultDataAccessException ex) {
    ErrorResponse error = new ErrorResponse(Arrays.asList("Cannot delete non-existing resource"));
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    ErrorResponse error = new ErrorResponse(Arrays.asList("Data Integrity Violation: we cannot process your request."));
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    Throwable cause = ex.getCause();
    if (cause instanceof InvalidFormatException invalidFormatException) {
      if (invalidFormatException.getTargetType().isEnum()) {
        Class<?> enumClass = invalidFormatException.getTargetType();
        Object[] enumConstants = enumClass.getEnumConstants();

        String allowedValues = Arrays.stream(enumConstants)
            .map(enumConstant -> enumConstant.toString())
            .reduce((e1, e2) -> e1 + ", " + e2)
            .orElse("");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(String.format("Invalid value '%s'. Allowed values are: %s",
                                invalidFormatException.getValue(), allowedValues));
      }
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Invalid request body: " + ex.getLocalizedMessage());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    List<String> errors = new ArrayList<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> errors.add(error.getDefaultMessage()));
    return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
  }
}
