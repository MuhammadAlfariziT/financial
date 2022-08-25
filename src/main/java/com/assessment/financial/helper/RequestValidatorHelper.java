package com.assessment.financial.helper;

import com.assessment.financial.constant.TransactionType;
import com.assessment.financial.constant.response.ResponseCode;
import com.assessment.financial.dto.MemberAndTransactionDto;
import com.assessment.financial.dto.ResponseDto;
import com.assessment.financial.dto.TransactionDto;
import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import reactor.core.publisher.Mono;

public class RequestValidatorHelper {

  public static Mono<ResponseDto> isValidRequestMember (MemberAndTransactionDto memberAndTransactionDto) {
    if (memberAndTransactionDto.getName().length() <= 3) {
      return Mono.just(
          ResponseDto.buildResponse()
              .responseCode(ResponseCode.NAME_LEN_MIN_3_CHARACTER)
              .build()
      );
    }

    if (Objects.isNull(memberAndTransactionDto.getName()) ||
        Objects.isNull(memberAndTransactionDto.getAddress()) ||
        Objects.isNull(memberAndTransactionDto.getBalance()) ||
        Objects.isNull(memberAndTransactionDto.getBirth_date())) {
      return Mono.just(
          ResponseDto.buildResponse()
              .responseCode(ResponseCode.ALL_FIELD_REQUIRED)
              .build()
      );
    }

    if (! isValidDate(memberAndTransactionDto.getBirth_date())) {
      return Mono.just(
          ResponseDto.buildResponse()
              .responseCode(ResponseCode.INVALID_DATE_FORMAT)
              .build()
      );
    }

    if (isFutureDate(memberAndTransactionDto.getBirth_date())) {
      return Mono.just(
          ResponseDto.buildResponse()
              .responseCode(ResponseCode.FUTURE_DATE_INPUT)
              .build()
      );
    }

    return null;
  }

  public static Mono<ResponseDto> isValidRequestTransaction (TransactionDto transactionDto) {
    if (! transactionDto.getTransaction_type().equals(TransactionType.DEPOSIT) &&
        ! transactionDto.getTransaction_type().equals(TransactionType.LOAN) &&
        ! transactionDto.getTransaction_type().equals(TransactionType.PAID_OFF) &&
        ! transactionDto.getTransaction_type().equals(TransactionType.WITHDRAW)) {
      return Mono.just(
          ResponseDto.buildResponse()
              .responseCode(ResponseCode.TRANSACTION_TYPE_NOT_RECOGIZED)
              .build()
      );
    }

    if (Objects.isNull(transactionDto.getTransaction_type()) ||
        Objects.isNull(transactionDto.getTransaction_date()) ||
        Objects.isNull(transactionDto.getAmount()) ||
        Objects.isNull(transactionDto.getMember_id())) {
      return Mono.just(
          ResponseDto.buildResponse()
              .responseCode(ResponseCode.ALL_FIELD_REQUIRED)
              .build()
      );
    }

    if (!isValidDate(transactionDto.getTransaction_date())) {
      return Mono.just(
          ResponseDto.buildResponse()
              .responseCode(ResponseCode.INVALID_DATE_FORMAT)
              .build()
      );
    }

    if (isFutureDate(transactionDto.getTransaction_date())) {
      return Mono.just(
          ResponseDto.buildResponse()
              .responseCode(ResponseCode.FUTURE_DATE_INPUT)
              .build()
      );
    }

    return null;
  }

  public static boolean isValidDate (String date) {
    Pattern datePattern = Pattern.compile("[0-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]");
    Matcher dateMatcher = datePattern.matcher(date);
    boolean isDateFormat = dateMatcher.find();

    return isDateFormat;
  }

  public static boolean isFutureDate (String date) {
    LocalDate now = LocalDate.now();

    return LocalDate.parse(date).isAfter(now);
  }
}
