package com.test.crm.exceptions;

public class NotAuthenticatedException extends RuntimeException {

  public NotAuthenticatedException(String message) {
    super(message);
  }
}
