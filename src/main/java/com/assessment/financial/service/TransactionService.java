package com.assessment.financial.service;

import com.assessment.financial.constant.TransactionType;
import com.assessment.financial.constant.response.ResponseCode;
import com.assessment.financial.dao.TransactionDao;
import com.assessment.financial.dto.TransactionDto;
import com.assessment.financial.dto.TransactionHistoryDto;
import com.assessment.financial.exception.BusinessLogicException;
import com.assessment.financial.mapper.MemberMapper;
import com.assessment.financial.mapper.TransactionMapper;
import com.assessment.financial.repository.MemberRepository;
import com.assessment.financial.repository.TransactionRepository;
import java.sql.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private MemberService memberService;

  public Mono<TransactionDto> insertOneTransaction (TransactionDto transactionDto) {
    return Mono.just(transactionDto)
        .map(transactionDtoEntity -> {
          log.info("Sampai sini");
          memberRepository.findById(transactionDto.getMember_id())
              .flatMap(memberDaoOptional -> Optional.ofNullable(memberDaoOptional)
                  .map(memberDao -> {
                    Long future_balance = memberDao.getBalance();

                    if (transactionDto.getTransaction_type().equals(TransactionType.PAID_OFF) || transactionDto.getTransaction_type().equals(TransactionType.DEPOSIT)) {
                      future_balance -= transactionDto.getAmount();
                    } else {
                      future_balance += transactionDto.getAmount();
                    }

                    memberDao.setBalance(future_balance);

                    return memberService.updateOneMember(transactionDto.getMember_id(), MemberMapper.memberDaoToMemberAndTransactionDto(memberDao));
                  })
              );
          TransactionDao transactionDao = TransactionMapper.transactionDtoToDao(transactionDtoEntity);
          transactionRepository.save(transactionDao);
          return TransactionMapper.transactionDaoToDto(transactionDao);
        })
        .doOnError(throwable -> {
          log.error(throwable.toString());
          throw new BusinessLogicException(ResponseCode.FAILED_CREATE_DATA);
        });
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
        .flatMap(Flux::fromIterable)
        .doOnError(throwable -> {
          log.error(throwable.toString());
          throw new BusinessLogicException(ResponseCode.FAILED_GET_DATA);
        });
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
        .flatMap(Flux::fromIterable)
        .doOnError(throwable -> {
          log.error(throwable.toString());
          throw new BusinessLogicException(ResponseCode.FAILED_GET_DATA);
        });

  }

  public Mono<Optional<TransactionDto>> updateOneTransaction (Long id, TransactionDto transactionDto) {
    try {
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
          )
      ).doOnError(throwable -> {
        log.error(throwable.toString());
        throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST);
      });
    } catch (Exception e) {
      throw new BusinessLogicException(ResponseCode.FAILED_UPDATE_DATA);
    }
  }

  public Mono<Void> deleteOneTransaction (Long id) {
    try {
      transactionRepository.deleteById(id);
      return Mono.empty();
    } catch (Exception e) {
      throw new BusinessLogicException(ResponseCode.FAILED_DELETE_DATA);
    }
  }
}
