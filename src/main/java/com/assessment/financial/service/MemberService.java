package com.assessment.financial.service;

import com.assessment.financial.constant.response.ResponseCode;
import com.assessment.financial.dao.MemberDao;
import com.assessment.financial.dto.MemberAndTransactionDto;
import com.assessment.financial.dto.MemberDto;
import com.assessment.financial.exception.BusinessLogicException;
import com.assessment.financial.mapper.MemberMapper;
import com.assessment.financial.repository.MemberRepository;
import java.sql.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MemberService {

  @Autowired
  private MemberRepository memberRepository;

  public Mono<MemberDto> insertMember (
      MemberAndTransactionDto memberAndTransactionDto) {
    return Mono.just(memberAndTransactionDto)
        .map(memberAndTransactionDtoEntity -> {
            MemberDao memberDao = MemberMapper.memberAndTransactionDtoToDao(memberAndTransactionDtoEntity);
            if (memberAndTransactionDto.getBalance() < 0){
              throw new BusinessLogicException(ResponseCode.SUFFICIENT_BALANCE);
            }

          memberRepository.save(memberDao);
            return MemberMapper.memberDaoToDto(memberDao);
        });
  }

  public Flux<MemberDto> getAllMember () {
    return Flux.fromIterable(
        memberRepository.findAll()).map(MemberMapper::memberDaoToDto)
        .doOnError(throwable -> {
          throw new BusinessLogicException(ResponseCode.FAILED_GET_DATA);
        });
  }

  public Mono<Optional<MemberAndTransactionDto>> getMemberById (Long id) {
      return Mono.just(memberRepository.findById(id)
              .map(MemberMapper::memberDaoToMemberAndTransactionDto))
              .doOnError(throwable -> {
                throw new BusinessLogicException(ResponseCode.FAILED_GET_DATA);
          });
  }

  public Mono<Optional<MemberDto>> updateMember (Long id, MemberAndTransactionDto memberAndTransactionDto) {
    return Mono.just(memberRepository.findById(id)
        .flatMap(memberDaoOptional -> Optional.ofNullable(memberDaoOptional)
            .map(memberDao -> {

              if (memberAndTransactionDto.getBalance() < 0){
                throw new BusinessLogicException(ResponseCode.SUFFICIENT_BALANCE);
              }

              memberDao.setName(memberAndTransactionDto.getName());
              memberDao.setBalance(memberAndTransactionDto.getBalance());
              memberDao.setAddress(memberAndTransactionDto.getAddress());
              memberDao.setBirth_date(Date.valueOf(memberAndTransactionDto.getBirth_date()));

              return memberRepository.save(memberDao);
            })
            .map(MemberMapper::memberDaoToDto)
        ));
  }

  public Mono<Void> deleteMember (Long id) {
    try {
      memberRepository.deleteById(id);
    } catch (BusinessLogicException error) {
      throw error;
    }

    return Mono.empty();
  }
}
