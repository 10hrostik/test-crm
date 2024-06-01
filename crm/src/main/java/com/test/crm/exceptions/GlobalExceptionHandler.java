package com.test.crm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = UsernameNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
    ErrorResponse errorResponse = new ErrorResponse(ErrorMessages.INVALID_CREDENTIALS, List.of(e.getMessage()));
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
  }

  @ExceptionHandler(value = UserExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserExistsException(UserExistsException e) {
    ErrorResponse errorResponse = new ErrorResponse(ErrorMessages.USERNAME_ALREADY_EXISTS, List.of(e.getMessage()));
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
  }
}
