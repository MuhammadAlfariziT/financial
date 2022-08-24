package com.assessment.financial.dao;

import com.assessment.financial.constant.datasource.MemberField;
import com.assessment.financial.constant.datasource.TableName;
import com.assessment.financial.constant.datasource.TransactionField;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableName.MEMBER)
public class MemberDao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = MemberField.NAME)
  private String name;

  @Column(name = MemberField.ADDRESS)
  private String address;

  @Column(name = MemberField.BALANCE)
  private Long balance;

  @Column(name = MemberField.BIRTH_DATE)
  private Date birth_date;

  @OneToMany(fetch = FetchType.EAGER, targetEntity = TransactionDao.class, cascade = CascadeType.ALL)
  @JoinColumn(name = TransactionField.MEMBER_ID, referencedColumnName = MemberField.ID)
  private List<TransactionDao> transaction_history;

  @Builder(builderMethodName = "buildMemberDao")
  public MemberDao (Long id, String name, String address, Date birth_date, Long balance, List<TransactionDao> transaction_history) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.birth_date = birth_date;
    this.balance = balance;
    this.transaction_history = transaction_history;
  }
}
