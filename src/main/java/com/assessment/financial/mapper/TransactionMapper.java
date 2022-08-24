package com.assessment.financial.mapper;

import com.assessment.financial.dao.TransactionDao;
import com.assessment.financial.dto.TransactionDto;
import java.sql.Date;

public class TransactionMapper {

  public static TransactionDto transactionDaoToDto (TransactionDao transactionDao) {
    return TransactionDto.buildTransactionDto()
        .id(transactionDao.getId())
        .member_id(transactionDao.getMember_id())
        .transaction_type(transactionDao.getTransaction_type())
        .transaction_date(transactionDao.getTransaction_date().toString())
        .amount(transactionDao.getAmount())
        .build();
  }

  public static TransactionDao transactionDtoToDao (TransactionDto transactionDto) {
    return TransactionDao.buildTransactionDao()
        .id(transactionDto.getId())
        .member_id(transactionDto.getMember_id())
        .transaction_type(transactionDto.getTransaction_type())
        .transaction_date(Date.valueOf(transactionDto.getTransaction_date()))
        .amount(transactionDto.getAmount())
        .build();
  }
}
