package com.assessment.financial.service;

import com.assessment.financial.dto.MemberAndTransactionDto;
import com.assessment.financial.dto.MemberDto;
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

  public Mono<MemberDto> insertOneMember (
      MemberAndTransactionDto memberAndTransactionDto) {
    return Mono.just(memberAndTransactionDto)
        .map(MemberMapper::memberAndTransactionDtoToDao)
        .map(memberRepository::save)
        .map(MemberMapper::memberDaoToDto);
  }

  public Flux<MemberDto> getAllMember () {
    return Flux.fromIterable(memberRepository.findAll())
        .map(MemberMapper::memberDaoToDto);
  }

  public Mono<Optional<MemberAndTransactionDto>> getMemberById (Long id) {
    return Mono.just(memberRepository.findById(id).map(MemberMapper::memberDaoToMemberAndTransactionDto));
  }

  public Mono<Optional<MemberDto>> updateOneMember (Long id, MemberAndTransactionDto memberAndTransactionDto) {
    return Mono.just(memberRepository.findById(id)
        .flatMap(memberDaoOptional -> Optional.ofNullable(memberDaoOptional)
            .map(memberDao -> {
              memberDao.setName(memberAndTransactionDto.getName());
              memberDao.setBalance(memberAndTransactionDto.getBalance());
              memberDao.setAddress(memberAndTransactionDto.getAddress());
              memberDao.setBirth_date(Date.valueOf(memberAndTransactionDto.getBirth_date()));

              return memberRepository.save(memberDao);
            })
            .map(MemberMapper::memberDaoToDto)
        ));
  }

  public Mono<Void> deleteOneMember (Long id) {
    memberRepository.deleteById(id);
    return Mono.empty();
  }
}
