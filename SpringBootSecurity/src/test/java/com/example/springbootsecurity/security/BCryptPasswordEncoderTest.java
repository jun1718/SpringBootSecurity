package com.example.springbootsecurity.security;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@DataJpaTest
@Slf4j
public class BCryptPasswordEncoderTest {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testEncode() {
        String rawPw = "1111";
        String encodedPw = passwordEncoder.encode(rawPw);

        assertThat(passwordEncoder.matches(rawPw, encodedPw)) // 항상 값은 바뀌지만 이전값으로 1111해도 된다. 즉 1111은 여러개의 다이제스트 값을 가질수 있다.
            .isTrue();

        log.debug("rawPw : {}", rawPw);
        log.debug("encodedPw : {}", encodedPw);
    }
}
