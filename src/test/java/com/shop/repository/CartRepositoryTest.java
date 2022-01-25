package com.shop.repository;

import com.shop.constant.Role;
import com.shop.entity.Cart;
import com.shop.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestConstructor;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class CartRepositoryTest {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("카트 가져오기")
    public void get() {

        Member member = Member.builder()
                .userName("홍길동")
                .userPw(passwordEncoder.encode("1234"))
                .userId("test@test.com")
                .role(Role.USER)
                .build();
        Member savedMember = memberRepository.save(member);

        Cart cart = Cart.builder()
                .member(savedMember)
                .build();

        cartRepository.save(cart);

        Member foundId = memberRepository.findByUserId("test@test.com");

        log.info("cart : {}", cartRepository.findByMember(member));
        log.info("cart : {}", cartRepository.findByMember(foundId));
    }

}