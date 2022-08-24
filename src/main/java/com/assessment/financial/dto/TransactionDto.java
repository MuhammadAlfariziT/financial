package com.assessment.financial.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionDto {

  private Long id;
  private Long member_id;
  private String transaction_type;
  private Long amount;
  private String transaction_date;

  @Builder(builderMethodName = "buildTransactionDto")
  public TransactionDto (Long id, Long member_id, String transaction_type, Long amount, String transaction_date) {
    this.id = id;
    this.member_id = member_id;
    this.transaction_type = transaction_type;
    this.amount = amount;
    this.transaction_date = transaction_date;
  }
}
