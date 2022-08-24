package com.assessment.financial.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberDto {

  private Long id;
  private String name;
  private String address;
  private String birth_date;
  private Long balance;

  @Builder(builderMethodName = "buildMemberDto")
  public MemberDto(Long id, String name, String address, String birth_date, Long balance) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.birth_date = birth_date;
    this.balance = balance;
  }
}
