package com.assessment.financial.dao;

import com.assessment.financial.constant.datasource.TableName;
import com.assessment.financial.constant.datasource.TransactionField;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = TableName.TRANSACTION)
public class TransactionDao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = TransactionField.MEMBER_ID)
  private Long member_id;

  @Column(name = TransactionField.TRANSACTION_TYPE)
  private String transaction_type;

  @Column(name = TransactionField.AMOUNT)
  private Long amount;

  @Column(name = TransactionField.TRANSACTION_DATE)
  private Date transaction_date;

  @Builder(builderMethodName = "buildTransactionDao")
  public TransactionDao (Long id, Long member_id, String transaction_type, Long amount, Date transaction_date) {
    this.id = id;
    this.member_id = member_id;
    this.transaction_type = transaction_type;
    this.amount = amount;
    this.transaction_date = transaction_date;
  }
}
