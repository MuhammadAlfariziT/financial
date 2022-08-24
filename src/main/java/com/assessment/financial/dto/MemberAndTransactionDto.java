package com.assessment.financial.dto;

import com.assessment.financial.dao.TransactionDao;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberAndTransactionDto {

  private Long id;
  private String name;
  private String address;
  private String birth_date;
  private Long balance;

  private List<TransactionDao> transaction_history;

  @Builder(builderMethodName = "buildMemberAndTransactionDto")
  public MemberAndTransactionDto(Long id, String name, String address, String birth_date, Long balance, List<TransactionDao> transaction_history) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.birth_date = birth_date;
    this.balance = balance;
    this.transaction_history = transaction_history;
  }
}
