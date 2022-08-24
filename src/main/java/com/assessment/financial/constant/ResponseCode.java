package com.assessment.financial.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

  SUCCESS(200, "Success");

  private int code;
  private String message;
}
