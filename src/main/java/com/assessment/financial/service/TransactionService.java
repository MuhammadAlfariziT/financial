package com.assessment.financial.service;

import com.assessment.financial.dto.TransactionDto;
import com.assessment.financial.dto.TransactionHistoryDto;
import com.assessment.financial.mapper.TransactionMapper;
import com.assessment.financial.repository.MemberRepository;
import com.assessment.financial.repository.TransactionRepository;
import java.sql.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private MemberRepository memberRepository;

  public Mono<TransactionDto> insertOneTransaction (TransactionDto transactionDto) {
    return Mono.just(transactionDto)
        .map(TransactionMapper::transactionDtoToDao)
        .map(transactionRepository::save)
        .map(TransactionMapper::transactionDaoToDto);
  }

  public Flux<TransactionHistoryDto> getAllTransactionHistory () {
    return Flux.fromIterable(memberRepository.findAll())
        .map(memberDao ->  memberDao.getTransaction_history().stream()
            .map(transactionDao -> TransactionHistoryDto
                .buildTransactionHistoryDto()
                .transaction_id(transactionDao.getId())
                .transaction_type(transactionDao.getTransaction_type())
                .name(memberDao.getName())
                .amount(transactionDao.getAmount())
                .date(transactionDao.getTransaction_date().toString())
                .build()
            ).collect(Collectors.toList())
        )
        .flatMap(Flux::fromIterable);
  }

  public Flux<TransactionHistoryDto> getTransactionHistoryRangeDate (String startDate, String endDate) {
    return Flux.fromIterable(memberRepository.findAll())
        .map(memberDao ->  memberDao.getTransaction_history().stream()
            .filter(transactionDao -> {
              boolean isAfterStartDate = transactionDao.getTransaction_date().compareTo(Date.valueOf(startDate)) >= 0;
              boolean isBeforeEndDate = transactionDao.getTransaction_date().compareTo(Date.valueOf(endDate)) <= 0;

              return isAfterStartDate && isBeforeEndDate;
            })
            .map(transactionDao -> TransactionHistoryDto
                .buildTransactionHistoryDto()
                .transaction_id(transactionDao.getId())
                .transaction_type(transactionDao.getTransaction_type())
                .name(memberDao.getName())
                .amount(transactionDao.getAmount())
                .date(transactionDao.getTransaction_date().toString())
                .build()
            ).collect(Collectors.toList())
        )
        .flatMap(Flux::fromIterable);

  }

  public Mono<Optional<TransactionDto>> updateOneTransaction (Long id, TransactionDto transactionDto) {
    return Mono.just(transactionRepository.findById(id)
        .flatMap(transactionDaoOptional -> Optional.ofNullable(transactionDaoOptional)
            .map(transactionDao -> {
              transactionDao.setMember_id(transactionDto.getMember_id());
              transactionDao.setTransaction_type(transactionDto.getTransaction_type());
              transactionDao.setAmount(transactionDto.getAmount());
              transactionDao.setTransaction_date(Date.valueOf(transactionDto.getTransaction_date()));

              return transactionRepository.save(transactionDao);
            })
            .map(TransactionMapper::transactionDaoToDto)
        ));
  }

  public Mono<Void> deleteOneTransaction (Long id) {
    transactionRepository.deleteById(id);
    return Mono.empty();
  }
}
