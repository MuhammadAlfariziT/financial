package com.assessment.financial.controller;

import com.assessment.financial.constant.ApiPath;
import com.assessment.financial.constant.response.ResponseCode;
import com.assessment.financial.dto.MemberAndTransactionDto;
import com.assessment.financial.dto.ResponseDto;
import com.assessment.financial.helper.RequestValidatorHelper;
import com.assessment.financial.service.MemberService;
import java.util.Objects;
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
@RequestMapping(ApiPath.BASE_MEMBER_PATH)
public class MemberController {

  @Autowired
  private MemberService memberService;

  @PostMapping
  public Mono<ResponseDto> insertOneMember (@RequestBody MemberAndTransactionDto memberAndTransactionDto) {

    if (!Objects.isNull(RequestValidatorHelper.isValidRequestMember(memberAndTransactionDto))) {
      return RequestValidatorHelper.isValidRequestMember(memberAndTransactionDto);
    }

    try {
      return memberService.insertOneMember(memberAndTransactionDto)
          .map(
              data -> ResponseDto.buildResponse()
                  .responseCode(ResponseCode.SUCCESS_CREATE_DATA)
                  .data(data)
                  .build()
          );
    } catch (Exception e) {
      return Mono.just(
          ResponseDto.buildResponse()
              .responseCode(ResponseCode.FAILED_CREATE_DATA)
              .build()
      );
    }
  }

  @GetMapping
  public Mono<ResponseDto> getAllMember () {
    try {
      return memberService.getAllMember()
          .collectList()
          .map(data -> ResponseDto.buildResponse()
              .responseCode(ResponseCode.SUCCESS_GET_DATA)
              .data(data)
              .build()
          );
    } catch (Exception e) {
      return Mono.just(
          ResponseDto.buildResponse()
              .responseCode(ResponseCode.FAILED_GET_DATA)
              .build()
      );
    }
  }

  @GetMapping(ApiPath.APPEND_PARAMS_ID)
  public Mono<ResponseDto> getMemberById (@PathVariable Long id) {
    try {
      return memberService.getMemberById(id)
          .map(
              memberDto -> ResponseDto.buildResponse()
                  .responseCode(ResponseCode.SUCCESS_GET_DATA)
                  .data(memberDto)
                  .build()
          );
    } catch (Exception e) {
      return Mono.just(
          ResponseDto.buildResponse()
              .responseCode(ResponseCode.FAILED_GET_DATA)
              .build()
      );
    }
  }

  @PutMapping(ApiPath.APPEND_PARAMS_ID)
  public Mono<ResponseDto> updateOneMember (@RequestBody MemberAndTransactionDto memberAndTransactionDto, @PathVariable Long id) {
    if (!Objects.isNull(RequestValidatorHelper.isValidRequestMember(memberAndTransactionDto))) {
      return RequestValidatorHelper.isValidRequestMember(memberAndTransactionDto);
    }

    try {
      return memberService.updateOneMember(id, memberAndTransactionDto)
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
  public Mono<ResponseDto> deleteOneMember (@PathVariable Long id) {
    try {
      return memberService.deleteOneMember(id)
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
