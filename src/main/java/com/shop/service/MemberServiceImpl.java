package com.shop.service;

import com.shop.constant.Role;
import com.shop.dto.SignupRequestDto;
import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    private void validateDuplicateMember(SignupRequestDto dto){
        Member findMember = memberRepository.findByUserId(dto.getUserId());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public void signup(SignupRequestDto dto) {

        validateDuplicateMember(dto);

        Member member = Member.builder()
                .userId(dto.getUserId())
                .userName(dto.getUserName())
                .userPw(passwordEncoder.encode(dto.getUserPw()))
                .role(Role.USER)
                .build();

        memberRepository.save(member);
    }
}
