package com.bitesync.api.exception;

public class DuplicateUserException extends RuntimeException {
  public DuplicateUserException(String username) {
    super(String.format("User with email: %s already exists", username));
  }
}
