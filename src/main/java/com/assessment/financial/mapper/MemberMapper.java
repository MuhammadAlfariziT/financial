package com.assessment.financial.mapper;

import com.assessment.financial.dao.MemberDao;
import com.assessment.financial.dto.MemberAndTransactionDto;
import com.assessment.financial.dto.MemberDto;
import java.sql.Date;

public class MemberMapper {

  public static MemberAndTransactionDto memberDaoToMemberAndTransactionDto (MemberDao memberDao) {
    return MemberAndTransactionDto.buildMemberAndTransactionDto()
        .id(memberDao.getId())
        .name(memberDao.getName())
        .address(memberDao.getAddress())
        .balance(memberDao.getBalance())
        .birth_date(memberDao.getBirth_date().toString())
        .transaction_history(memberDao.getTransaction_history())
        .build();
  }

  public static MemberDao memberAndTransactionDtoToDao (MemberAndTransactionDto memberAndTransactionDto) {
    return MemberDao.buildMemberDao()
        .id(memberAndTransactionDto.getId())
        .name(memberAndTransactionDto.getName())
        .address(memberAndTransactionDto.getAddress())
        .birth_date(Date.valueOf(memberAndTransactionDto.getBirth_date()))
        .balance(memberAndTransactionDto.getBalance())
        .build();
  }

  public static MemberDto memberDaoToDto (MemberDao memberDao) {
    return MemberDto.buildMemberDto()
        .id(memberDao.getId())
        .name(memberDao.getName())
        .address(memberDao.getAddress())
        .balance(memberDao.getBalance())
        .birth_date(memberDao.getBirth_date().toString())
        .build();
  }

  public static MemberDto memberTransactionDtoToMemberDto (MemberAndTransactionDto memberAndTransactionDto) {
    return MemberDto.buildMemberDto()
        .id(memberAndTransactionDto.getId())
        .name(memberAndTransactionDto.getName())
        .address(memberAndTransactionDto.getAddress())
        .balance(memberAndTransactionDto.getBalance())
        .birth_date(memberAndTransactionDto.getBirth_date().toString())
        .build();
  }

  public static MemberDao memberDtoToDao (MemberDto memberDto) {
    return MemberDao.buildMemberDao()
        .id(memberDto.getId())
        .name(memberDto.getName())
        .balance(memberDto.getBalance())
        .birth_date(Date.valueOf(memberDto.getBirth_date()))
        .address(memberDto.getAddress())
        .build();
  }
}
