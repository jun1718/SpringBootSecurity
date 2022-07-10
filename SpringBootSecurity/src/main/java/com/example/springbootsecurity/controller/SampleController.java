package com.example.springbootsecurity.controller;

import com.example.springbootsecurity.dto.AuthMemberDto;
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
    public void exMember(@AuthenticationPrincipal AuthMemberDto authMemberDto) {
        log.info("exMember.....................");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthMemberDto authMemberDto0 = (AuthMemberDto) authentication.getPrincipal();

        log.info("{}", authMemberDto);
        log.info("{}", authMemberDto0.getName());
        log.info("{}", authMemberDto.getName());
        log.info("{}", authMemberDto.getPassword());
        log.info("{}", authMemberDto.getAuthorities());
        log.info("{}", authMemberDto.getEmail());
    }

    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin...................");
    }
}
