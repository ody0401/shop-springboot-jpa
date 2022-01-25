package com.shop.common.security;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load User By UserName : {}", username);

        Member member = memberRepository.findByUserId(username);
        log.info("member : {}", member);

        User user = (User) User.builder().
                username(member.getUserId())
                .password(member.getUserPw())
                .roles(member.getRole().toString())
                .build();

        return member == null ? null : user;
    }
}
