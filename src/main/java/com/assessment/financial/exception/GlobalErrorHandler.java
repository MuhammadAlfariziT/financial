package com.assessment.financial.exception;

import com.assessment.financial.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalErrorHandler {

  @ExceptionHandler(BusinessLogicException.class)
  public ResponseEntity<Object> handleBusinessLogicException (BusinessLogicException businessLogicException) {
    log.info("DSFKSDJGKLEJRWG");
    ResponseDto responseDto = new ResponseDto(businessLogicException.getCode(),
        businessLogicException.getMessage(), null);

    log.info(responseDto.toString());

    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).contentType(MediaType.APPLICATION_JSON_UTF8).body(responseDto);
  }
}
