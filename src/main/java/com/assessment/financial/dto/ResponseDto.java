package com.assessment.financial.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDto {

  private int status_code;
  private String message;
  private Object data;

  @Builder(builderMethodName = "buildResponse")
  public ResponseDto (int status_code, String message, Object data) {
    this.status_code = status_code;
    this.message = message;
    this.data = data;
  }
}
