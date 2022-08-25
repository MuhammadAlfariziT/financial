package com.assessment.financial.exception;

import com.assessment.financial.constant.response.ResponseCode;

public class BusinessLogicException extends RuntimeException{
  private static final long serialVersionUID = 1L;

  private int code;
  private String message;

  public BusinessLogicException (ResponseCode responseCode) {
    super(responseCode.getMessage());

    this.code = responseCode.getCode();
    this.message = responseCode.getMessage();
  }

  @Override
  public String toString() {
    return "BusinessLogicException{" +
        "code='" + code + '\'' +
        ", message='" + message + '\'' +
        '}';
  }
}
