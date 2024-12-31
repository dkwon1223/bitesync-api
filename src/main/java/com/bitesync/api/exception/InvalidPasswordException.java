package com.bitesync.api.exception;

public class InvalidPasswordException extends RuntimeException {
  public InvalidPasswordException() {
    super("Password must be 8-20 characters long, include at least one digit, one uppercase letter, one lowercase letter, and one special character, and must not contain spaces.");
  }
}
