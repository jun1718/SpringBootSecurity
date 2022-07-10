package com.example.springbootsecurity.controller;

import com.example.springbootsecurity.dto.AuthMemberDto;
import com.example.springbootsecurity.dto.Oauth2MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/sample")
public class SampleController {
    @GetMapping("/all")
    public void exAll() {
        log.info("exAll..................");
    }

    @GetMapping("/member")
//    public void exMember(@AuthenticationPrincipal AuthMemberDto authMemberDto) { // 자사로그인시엔 이걸로하고 oauth2시에는 아래걸로해야한다. 사실 아래걸로 다 되게할수있는데 이전과 구분을 위해 이대로둔다.
    // 아래걸로 한번에 하길원할시에는 CustomUserDetailsService에를 AuthMemberDto가 아닌 Oauth2MemberDto를 사용하도록 적용해주면 된다.
    public void exMember(@AuthenticationPrincipal Oauth2MemberDto authMemberDto) {
        log.info("exMember.....................");

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        AuthMemberDto authMemberDto0 = (AuthMemberDto) authentication.getPrincipal();

        log.info("{}", authMemberDto);
//        log.info("{}", authMemberDto0.getName());
//        log.info("{}", authMemberDto.getName());
//        log.info("{}", authMemberDto.getPassword());
//        log.info("{}", authMemberDto.getAuthorities());
//        log.info("{}", authMemberDto.getEmail());
    }

    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin...................");
    }
}
