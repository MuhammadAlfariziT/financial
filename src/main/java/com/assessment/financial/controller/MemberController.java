package com.assessment.financial.controller;

import com.assessment.financial.constant.ApiPath;
import com.assessment.financial.constant.ResponseCode;
import com.assessment.financial.dto.MemberDto;
import com.assessment.financial.dto.ResponseDto;
import com.assessment.financial.service.MemberService;
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
  public Mono<ResponseDto> insertOneMember (@RequestBody MemberDto memberDto) {
    return memberService.insertOneMember(memberDto)
        .map(
            data -> ResponseDto.buildResponse()
                .status_code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .data(data)
                .build()
        );
  }

  @GetMapping
  public Mono<ResponseDto> getAllMember () {
    return memberService.getAllMember()
        .collectList()
        .map(data -> ResponseDto.buildResponse()
              .status_code(ResponseCode.SUCCESS.getCode())
              .message(ResponseCode.SUCCESS.getMessage())
              .data(data)
              .build()
        );
  }

  @GetMapping(ApiPath.APPEND_PARAMS_ID)
  public Mono<ResponseDto> getMemberById (@PathVariable Long id) {
    return memberService.getMemberById(id)
        .map(
            memberDto -> ResponseDto.buildResponse()
                .status_code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .data(memberDto)
                .build()
        );
  }

  @PutMapping(ApiPath.APPEND_PARAMS_ID)
  public Mono<ResponseDto> updateOneMember (@RequestBody MemberDto memberDto, @PathVariable Long id) {
    return memberService.updateOneMember(id, memberDto)
        .map(
            data -> ResponseDto.buildResponse()
                .status_code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .data(data)
                .build()
        );
  }

  @DeleteMapping(ApiPath.APPEND_PARAMS_ID)
  public Mono<ResponseDto> deleteOneMember (@PathVariable Long id) {
    return memberService.deleteOneMember(id)
        .thenReturn(
            ResponseDto.buildResponse()
                .status_code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .data(null)
                .build()
        );
  }
}
