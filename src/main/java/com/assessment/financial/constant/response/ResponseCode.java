package com.assessment.financial.constant.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

  BAD_RESPONSE(400, Message.BAD_RESPONSE),
  DATA_NOT_EXIST(404, Message.DATA_NOT_EXIST),
  NAME_LEN_MIN_3_CHARACTER(400, Message.NAME_LEN_UNDER_3_CHAR),
  SUCCESS_CREATE_DATA(200, Message.SUCCESS_CREATE_DATA),
  SUCCESS_GET_DATA(200, Message.SUCCESS_GET_DATA),
  SUCCESS_DELETE_DATA(200, Message.SUCCESS_DELETE_DATA),
  SUCCESS_UPDATE_DATA(200, Message.SUCCESS_UPDATE_DATA),
  SUFFICIENT_BALANCE(400, Message.BALANCE_UNDER_0),
  SUCCESS(200, Message.SUCCESS);

  private int code;
  private String message;
}
