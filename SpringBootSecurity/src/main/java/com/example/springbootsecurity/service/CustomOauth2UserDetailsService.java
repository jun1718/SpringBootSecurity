package com.example.springbootsecurity.service;

import com.example.springbootsecurity.dto.Oauth2MemberDto;
import com.example.springbootsecurity.entity.Member;
import com.example.springbootsecurity.entity.MemberRole;
import com.example.springbootsecurity.repository.MemberRepository;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOauth2UserDetailsService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        log.info("{}", userRequest);
        log.info("clientName = {}", userRequest.getClientRegistration().getClientName());
        log.info("additionalParameters {}", userRequest.getAdditionalParameters());

        OAuth2User user = super.loadUser(userRequest);
        user.getAttributes().forEach((k, v) -> log.info(k + " : " + v));


        String email = user.getAttribute("email");
        Member member = saveSocailMember(email);


        Oauth2MemberDto oauth2MemberDto = new Oauth2MemberDto(member.getEmail(), member.getPassword(), member.isFromSocial(),
            member.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet()), user.getAttributes());
        oauth2MemberDto.setName(member.getName());
        return oauth2MemberDto;
    }

    private Member saveSocailMember(String email) {
        Optional<Member> result = memberRepository.findById(email);
        if (result.isPresent()) {
            return result.get();
        }

        Member member
            = Member.builder()
            .name(email)
            .email(email)
            .password(passwordEncoder.encode("1111"))
            .fromSocial(true)
            .build();

        member.addMemberRole(MemberRole.USER);
        memberRepository.save(member);
        return member;
    }
}
