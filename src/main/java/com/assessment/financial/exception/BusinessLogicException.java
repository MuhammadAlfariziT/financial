package com.assessment.financial.exception;

import com.assessment.financial.constant.response.ResponseCode;

public class BusinessLogicException extends RuntimeException{

  private int code;
  private String message;

  public BusinessLogicException (ResponseCode responseCode) {
    super();

    this.code = responseCode.getCode();
    this.message = responseCode.getMessage();
  }
}
