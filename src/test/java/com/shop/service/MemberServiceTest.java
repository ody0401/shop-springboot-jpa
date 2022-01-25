package com.shop.service;

import com.shop.constant.Role;
import com.shop.dto.SignupRequestDto;
import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class MemberServiceTest {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Transactional
    @Test
    @DisplayName("회원가입 테스트")
    public void signupSaveTest(){

        //given
        Member member = Member.builder()
                .userId("user")
                .userName("홍길동")
                .userPw(passwordEncoder.encode("1234"))
                .role(Role.USER)
                .build();

        //when
        Member savedMember = memberRepository.save(member);

        //then
        log.info("로그인 유저 정보: {}", savedMember);
    }

    private SignupRequestDto createMemberDto() {
        SignupRequestDto dto = new SignupRequestDto();
        dto.setUserId("test@test.com");
        dto.setUserName("홍길동");
        dto.setUserPw("1234");

        return dto;
    }

    @Transactional
    @Test
    @DisplayName("중복 회원가입 테스트")
    public void saveDuplicateMemberTest(){

        //given
        SignupRequestDto member1 = createMemberDto();
        SignupRequestDto member2 = createMemberDto();

        //when
        memberService.signup(member1);
        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.signup(member2);
        });

        //then
        assertEquals("이미 가입된 회원입니다.", e.getMessage());

    }

}