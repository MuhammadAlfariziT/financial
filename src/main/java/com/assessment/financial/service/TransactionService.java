package com.assessment.financial.service;

import com.assessment.financial.dto.TransactionDto;
import com.assessment.financial.mapper.TransactionMapper;
import com.assessment.financial.repository.TransactionRepository;
import java.sql.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  public Mono<TransactionDto> insertOneTransaction (TransactionDto transactionDto) {
    return Mono.just(transactionDto)
        .map(TransactionMapper::transactionDtoToDao)
        .map(transactionRepository::save)
        .map(TransactionMapper::transactionDaoToDto);
  }
  public Flux<TransactionDto> getAllTransaction () {
    return Flux.fromIterable(transactionRepository.findAll())
        .map(TransactionMapper::transactionDaoToDto);
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
