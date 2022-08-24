package com.assessment.financial.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

  BAD_RESPONSE(400, "Bad Response"),
  SUCCESS(200, "Success");

  private int code;
  private String message;
}
