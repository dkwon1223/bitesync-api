package com.bitesync.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bitesync.api.exception.DuplicateUserException;
import com.bitesync.api.exception.EntityNotFoundException;
import com.bitesync.api.exception.ErrorResponse;
import com.bitesync.api.exception.InsufficientInventoryException;
import com.bitesync.api.exception.InvalidPasswordException;
import com.bitesync.api.exception.InventoryItemNotFoundException;
import com.bitesync.api.exception.MenuItemNotFoundException;
import com.bitesync.api.exception.MenuItemUnavailableException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({EntityNotFoundException.class, MenuItemNotFoundException.class, InventoryItemNotFoundException.class, })
  public ResponseEntity<Object> handleResourceNotFoundException(RuntimeException ex) {
    ErrorResponse error = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InsufficientInventoryException.class)
  public ResponseEntity<ErrorResponse> handleInsufficientInventory(InsufficientInventoryException ex) {
    ErrorResponse error = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MenuItemUnavailableException.class)
  public ResponseEntity<ErrorResponse> handleMenuItemUnavailable(MenuItemUnavailableException ex) {
    ErrorResponse error = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DuplicateUserException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateUser(DuplicateUserException ex) {
    ErrorResponse error = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidPasswordException.class)
  public ResponseEntity<ErrorResponse> handleInvalidPassword(InvalidPasswordException ex) {
    ErrorResponse error = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
    ErrorResponse error = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.OK);
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ResponseEntity<Object> handleDataAccessException(EmptyResultDataAccessException ex) {
    ErrorResponse error = new ErrorResponse("Cannot delete non-existing resource");
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    ErrorResponse error = new ErrorResponse("Data Integrity Violation: we cannot process your request.");
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
            .body(String.format("Invalid status '%s'. Allowed values are: %s",
                                invalidFormatException.getValue(), allowedValues));
      }
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Invalid request body: " + ex.getLocalizedMessage());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    BindingResult bindingResult = ex.getBindingResult();
    List<String> errorMessages = new ArrayList<>();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      errorMessages.add(fieldError.getDefaultMessage());
    }

    ErrorResponse errorResponse = new ErrorResponse(String.join(", ", errorMessages));

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }
}
