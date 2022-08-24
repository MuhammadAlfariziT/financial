package com.assessment.financial.mapper;

import com.assessment.financial.dao.MemberDao;
import com.assessment.financial.dto.MemberDto;
import java.sql.Date;

public class MemberMapper {

  public static MemberDto memberDaoToDto (MemberDao memberDao) {
    return MemberDto.buildMemberDto()
        .id(memberDao.getId())
        .name(memberDao.getName())
        .address(memberDao.getAddress())
        .balance(memberDao.getBalance())
        .birth_date(memberDao.getBirth_date().toString())
        .build();
  }

  public static MemberDao memberDtoToDao (MemberDto memberDto) {
    return MemberDao.buildMemberDao()
        .id(memberDto.getId())
        .name(memberDto.getName())
        .address(memberDto.getAddress())
        .birth_date(Date.valueOf(memberDto.getBirth_date()))
        .balance(memberDto.getBalance())
        .build();
  }
}
