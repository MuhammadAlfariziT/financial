package com.assessment.financial.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionHistoryDto {

  private Long transaction_id;
  private String name;
  private String transaction_type;
  private Long amount;
  private String date;

  @Builder(builderMethodName = "buildTransactionHistoryDto")
  public TransactionHistoryDto (Long transaction_id, String name, String transaction_type, Long amount, String date) {
    this.transaction_id = transaction_id;
    this.name = name;
    this.transaction_type = transaction_type;
    this.amount = amount;
    this.date = date;
  }
}
