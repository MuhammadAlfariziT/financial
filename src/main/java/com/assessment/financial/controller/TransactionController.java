package com.assessment.financial.controller;

import com.assessment.financial.constant.ApiPath;
import com.assessment.financial.constant.ResponseCode;
import com.assessment.financial.dto.ResponseDto;
import com.assessment.financial.dto.TransactionDto;
import com.assessment.financial.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiPath.BASE_TRANSACTION_PATH)
public class TransactionController {

  @Autowired
  private TransactionService transactionService;


  @PostMapping
  public Mono<ResponseDto> insertOneTransaction (@RequestBody TransactionDto transactionDto) {
    return transactionService.insertOneTransaction(transactionDto)
        .map(
            data -> ResponseDto.buildResponse()
                .status_code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .data(data)
                .build()
        );
  }

  @GetMapping
  public Mono<ResponseDto> getAllTransaction () {
    return transactionService.getAllTransaction()
        .collectList()
        .map(data -> ResponseDto.buildResponse()
            .status_code(ResponseCode.SUCCESS.getCode())
            .message(ResponseCode.SUCCESS.getMessage())
            .data(data)
            .build()
        );
  }

  @PutMapping(ApiPath.APPEND_PARAMS_ID)
  public Mono<ResponseDto> updateOneTransaction (@RequestBody TransactionDto transactionDto, @PathVariable Long id) {
    return transactionService.updateOneTransaction(id, transactionDto)
        .map(
            data -> ResponseDto.buildResponse()
                .status_code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .data(data)
                .build()
        );
  }

  @DeleteMapping(ApiPath.APPEND_PARAMS_ID)
  public Mono<ResponseDto> deleteOneTransaction (@PathVariable Long id) {
    return transactionService.deleteOneTransaction(id)
        .thenReturn(
            ResponseDto.buildResponse()
                .status_code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .data(null)
                .build()
        );
  }

}
