package com.example.springbootsecurity.dto;

import java.util.Collection;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Slf4j
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Oauth2MemberDto extends User implements OAuth2User {
    private String email;
    private String password;
    private String name;
    private Boolean fromSocial;
    private Map<String, Object> attr;

    public Oauth2MemberDto(String username, String password, Boolean fromSocial,
                           Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        this(username, password, fromSocial, authorities);
        this.attr = attr;
    }

    public Oauth2MemberDto(String username, String password, Boolean fromSocial,
                           Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.fromSocial = fromSocial;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
