package com.assessment.financial.service;

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

  public Mono<MemberDto> insertOneMember (MemberDto memberDto) {
    return Mono.just(memberDto)
        .map(MemberMapper::memberDtoToDao)
        .map(memberRepository::save)
        .map(MemberMapper::memberDaoToDto);
  }
  public Flux<MemberDto> getAllMember () {
    return Flux.fromIterable(memberRepository.findAll())
        .map(MemberMapper::memberDaoToDto);
  }

  public Mono<Optional<MemberDto>> getMemberById (Long id) {
    return Mono.just(memberRepository.findById(id).map(MemberMapper::memberDaoToDto));
  }

  public Mono<Optional<MemberDto>> updateOneMember (Long id, MemberDto memberDto) {
    return Mono.just(memberRepository.findById(id)
        .flatMap(memberDaoOptional -> Optional.ofNullable(memberDaoOptional)
            .map(memberDao -> {
              memberDao.setName(memberDto.getName());
              memberDao.setBalance(memberDto.getBalance());
              memberDao.setAddress(memberDto.getAddress());
              memberDao.setBirth_date(Date.valueOf(memberDto.getBirth_date()));

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
