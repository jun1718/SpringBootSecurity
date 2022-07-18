package shop.gaship.gashipfront.security.social.dto.domain;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class Member {
    private String email;
    private List<String> authorities;
    private String password;
}
