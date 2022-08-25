package com.assessment.financial.dto;

import com.assessment.financial.constant.response.ResponseCode;
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
  public ResponseDto (ResponseCode responseCode, Object data) {
    this.status_code = responseCode.getCode();
    this.message = responseCode.getMessage();
    this.data = data;
  }

  @Builder(builderMethodName = "buildResponseException")
  public ResponseDto (int status_code, String message, Object data) {
    this.status_code = status_code;
    this.message = message;
    this.data = data;
  }
}
