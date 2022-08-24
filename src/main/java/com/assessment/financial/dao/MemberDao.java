package com.assessment.financial.dao;

import com.assessment.financial.constant.TableName;
import com.assessment.financial.constant.field.MemberField;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

  @Builder(builderMethodName = "buildMemberDao")
  public MemberDao (Long id, String name, String address, Date birth_date, Long balance) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.birth_date = birth_date;
    this.balance = balance;
  }
}
