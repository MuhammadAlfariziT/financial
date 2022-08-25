package com.assessment.financial.constant.response;

public class Message {
  public static final String DATA_NOT_EXIST = "Data Not Exist";
  public static final String NAME_LEN_UNDER_3_CHAR = "Name length must greater than 3 character";
  public static final String BAD_RESPONSE = "Bad Response";
  public static final String SUCCESS = "Success";
  public static final String SUCCESS_GET_DATA = SUCCESS + " Get Data !";
  public static final String SUCCESS_UPDATE_DATA = SUCCESS + " Update Data !";
  public static final String SUCCESS_DELETE_DATA = SUCCESS + " Delete Data !";
  public static final String SUCCESS_CREATE_DATA = SUCCESS + " Create New Data !";
  public static final String FAILED = "Failed";
  public static final String FAILED_GET_DATA = FAILED + " Get Data !";
  public static final String FAILED_UPDATE_DATA = FAILED + " Update Data !";
  public static final String FAILED_DELETE_DATA = FAILED + " Delete Data !";
  public static final String FAILED_CREATE_DATA = FAILED + " Create New Data !";
  public static final String ALL_FIELD_REQUIRED = "All field is Required!";
  public static final String TRANSACTION_TYPE_NOT_RECOGNIZED = "Transaction Type Not Recognized. Valid value : (Deposit, Withdraw, Loan, Paid Off)";
  public static final String BALANCE_UNDER_0 = "Sufficient Balance!";
  public static final String INVALID_DATE_FORMAT = "Invalid Date Format! please (yyyy-MM-dd)";
  public static final String FUTURE_DATE_INPUT = "The date entered has not occurred";
}
