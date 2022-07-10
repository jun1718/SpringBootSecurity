package com.example.springbootsecurity.dto;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class AuthMemberDto extends User {
    private String email;
    private String name;
    private Boolean fromSocial;

    public AuthMemberDto(String username, String password, Boolean fromSocial,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.fromSocial = fromSocial;
    }
}
