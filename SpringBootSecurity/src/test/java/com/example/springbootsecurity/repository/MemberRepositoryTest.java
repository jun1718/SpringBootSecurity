package com.example.springbootsecurity.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.springbootsecurity.entity.Member;
import com.example.springbootsecurity.entity.MemberRole;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void insertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member =
                Member.builder()
                    .email("member" + i + "@gaship.com")
                    .name("사용자" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            member.addMemberRole(MemberRole.USER);

            if (i >= 80) {
                member.addMemberRole(MemberRole.MANAGER);
            }

            if (i >= 90) {
                member.addMemberRole(MemberRole.ADMIN);
            }

            memberRepository.save(member);
        });
    }

    @Test
    void findTest() {

        Member member = memberRepository.findById("member1@gaship.com").orElseThrow();
        assertThat(member.getName())
            .isEqualTo("사용자1");

        System.out.println(member);
        System.out.println(member.getRoles());
    }

    @Test
    void getTest() {
        Member member = memberRepository.getReferenceById("member1@gaship.com");
        assertThat(member.getName())
            .isEqualTo("사용자1");
        System.out.println(member);
        System.out.println(member.getRoles());
    }
}