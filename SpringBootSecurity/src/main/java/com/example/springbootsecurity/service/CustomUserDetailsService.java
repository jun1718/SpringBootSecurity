package com.example.springbootsecurity.service;

import com.example.springbootsecurity.dto.AuthMemberDto;
import com.example.springbootsecurity.entity.Member;
import com.example.springbootsecurity.repository.MemberRepository;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername---------- {}", username);

        Member member = memberRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Check Email or Social"));
        log.info("", member);

        AuthMemberDto authMemberDto = new AuthMemberDto(member.getEmail(), member.getPassword(), member.isFromSocial(),
            member.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet()));
        authMemberDto.setName(member.getName());


        return authMemberDto;
    }
}
