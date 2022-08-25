package com.assessment.financial.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

  @ExceptionHandler(BusinessLogicException.class)
  public ResponseEntity<String> handleBusinessLogicException (BusinessLogicException businessLogicException) {
    return ResponseEntity.status(businessLogicException.getCode()).body(businessLogicException.getMessage());
  }
}
