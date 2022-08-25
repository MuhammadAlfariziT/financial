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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class MemberService {

  @Autowired
  private MemberRepository memberRepository;

  public Mono<MemberDto> insertOneMember (
      MemberAndTransactionDto memberAndTransactionDto) {
    return Mono.just(memberAndTransactionDto)
        .map(memberAndTransactionDtoEntity -> {
            MemberDao memberDao = MemberMapper.memberAndTransactionDtoToDao(memberAndTransactionDtoEntity);
            memberRepository.save(memberDao);
            return MemberMapper.memberDaoToDto(memberDao);
        })
        .doOnError(throwable -> {
          log.error(throwable.toString());
          throw new BusinessLogicException(ResponseCode.FAILED_CREATE_DATA);
        });
  }

  public Flux<MemberDto> getAllMember () {
    return Flux.fromIterable(
        memberRepository.findAll()).map(MemberMapper::memberDaoToDto)
        .doOnError(throwable -> {
          log.error(throwable.toString());
          throw new BusinessLogicException(ResponseCode.FAILED_GET_DATA);
        });
  }

  public Mono<Optional<MemberAndTransactionDto>> getMemberById (Long id) {
    return Mono.just(
        memberRepository.findById(id)
            .map(MemberMapper::memberDaoToMemberAndTransactionDto))
            .doOnError(throwable -> {
              log.error(throwable.toString());
              throw new BusinessLogicException(ResponseCode.FAILED_GET_DATA);
        });
  }

  public Mono<Optional<MemberDto>> updateOneMember (Long id, MemberAndTransactionDto memberAndTransactionDto) {
    try {
      return Mono.just(memberRepository.findById(id)
          .flatMap(memberDaoOptional -> Optional.ofNullable(memberDaoOptional)
              .map(memberDao -> {

                if (memberDao.getBalance() < 0){
                  throw new BusinessLogicException(ResponseCode.SUFFICIENT_BALANCE);
                }

                memberDao.setName(memberAndTransactionDto.getName());
                memberDao.setBalance(memberAndTransactionDto.getBalance());
                memberDao.setAddress(memberAndTransactionDto.getAddress());
                memberDao.setBirth_date(Date.valueOf(memberAndTransactionDto.getBirth_date()));

                return memberRepository.save(memberDao);
              })
              .map(MemberMapper::memberDaoToDto)
          )).doOnError(throwable -> {
        log.error(throwable.toString());
        throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST);
      });
    } catch (Exception e) {
      throw new BusinessLogicException(ResponseCode.FAILED_CREATE_DATA);
    }
  }

  public Mono<Void> deleteOneMember (Long id) {
    try {
      memberRepository.deleteById(id);
    } catch (Exception e) {
      throw new BusinessLogicException(ResponseCode.FAILED_DELETE_DATA);
    }

    return Mono.empty();
  }
}
