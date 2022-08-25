package com.assessment.financial.controller;

import com.assessment.financial.constant.ApiPath;
import com.assessment.financial.constant.response.ResponseCode;
import com.assessment.financial.dto.ResponseDto;
import com.assessment.financial.dto.TransactionDto;
import com.assessment.financial.exception.BusinessLogicException;
import com.assessment.financial.helper.RequestValidatorHelper;
import com.assessment.financial.service.TransactionService;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiPath.BASE_TRANSACTION_PATH)
@Slf4j
public class TransactionController {

  @Autowired
  private TransactionService transactionService;


  @PostMapping
  public Mono<ResponseDto> insertOneTransaction (@RequestBody TransactionDto transactionDto) {
    if (!Objects.isNull(RequestValidatorHelper.isValidRequestTransaction(transactionDto))) {
      return RequestValidatorHelper.isValidRequestTransaction(transactionDto);
    }

    try {
      return transactionService.insertOneTransaction(transactionDto)
          .map(
              data -> ResponseDto.buildResponse()
                  .responseCode(ResponseCode.SUCCESS_CREATE_DATA)
                  .data(data)
                  .build()
          );
    } catch (BusinessLogicException error) {
      log.info("ASDASDASD");
      return Mono.just(
          ResponseDto.buildResponseException()
              .status_code(error.getCode())
              .message(error.getMessage())
              .data(null)
              .build()
      );
    }
  }

  @GetMapping
  public Mono<ResponseDto> getAllTransaction (@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
    try {
      if (!Objects.isNull(startDate) && !Objects.isNull(endDate)) {
        return transactionService.getTransactionHistoryRangeDate(startDate, endDate)
            .collectList()
            .map(data -> ResponseDto.buildResponse()
                .responseCode(ResponseCode.SUCCESS_GET_DATA)
                .data(data)
                .build()
            );
      } else if (Objects.isNull(startDate) && Objects.isNull(endDate)){
        return transactionService.getAllTransactionHistory()
            .collectList()
            .map(data -> ResponseDto.buildResponse()
                .responseCode(ResponseCode.SUCCESS_GET_DATA)
                .data(data)
                .build()
            );
      }
    } catch (BusinessLogicException error) {
      return Mono.just(ResponseDto
          .buildResponseException()
              .status_code(error.getCode())
              .message(error.getMessage())
              .data(null)
              .build()
      );
    }
    return Mono.just(ResponseDto
        .buildResponse()
            .responseCode(ResponseCode.BAD_RESPONSE)
            .build()
        );
  }

  @PutMapping(ApiPath.APPEND_PARAMS_ID)
  public Mono<ResponseDto> updateOneTransaction (@RequestBody TransactionDto transactionDto, @PathVariable Long id) {
    if (!Objects.isNull(RequestValidatorHelper.isValidRequestTransaction(transactionDto))) {
      return RequestValidatorHelper.isValidRequestTransaction(transactionDto);
    }

    try {
      return transactionService.updateOneTransaction(id, transactionDto)
          .map(
              data -> ResponseDto.buildResponse()
                  .responseCode(ResponseCode.SUCCESS_UPDATE_DATA)
                  .data(data)
                  .build()
          );
    } catch (Exception e) {
      return Mono.just(
          ResponseDto.buildResponse()
              .responseCode(ResponseCode.FAILED_UPDATE_DATA)
              .build()
      );
    }
  }

  @DeleteMapping(ApiPath.APPEND_PARAMS_ID)
  public Mono<ResponseDto> deleteOneTransaction (@PathVariable Long id) {
    try {
      return transactionService.deleteOneTransaction(id)
          .thenReturn(
              ResponseDto.buildResponse()
                  .responseCode(ResponseCode.SUCCESS_DELETE_DATA)
                  .build()
          );
    } catch (Exception e) {
      return Mono.just(
          ResponseDto.buildResponse()
              .responseCode(ResponseCode.FAILED_DELETE_DATA)
              .build()
      );
    }
  }
}
