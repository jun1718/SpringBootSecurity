package shop.gaship.gashipfront.security.social.dto;

import java.util.Map;
import lombok.Builder;
/**
 * packageName    : shop.gaship.gashipfront.security.social.dto
 * fileName       : Oauth2Attributes
 * author         : choi-gyeom-jun
 * date           : 2022-07-13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-13        choi-gyeom-jun       최초 생성
 */
public class OAuth2Attributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;
    private String gender;
    private String birthday;

    @Builder
    public OAuth2Attributes(Map<String, Object> attributes, String nameAttributeKey,
                            String email, String gender, String birthday) {
        this.nameAttributeKey = nameAttributeKey;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
    }

    public static OAuth2Attributes of(String nameAttributeKey, String email, String gender, String birthday) {
        return OAuth2Attributes.builder()
            .nameAttributeKey(nameAttributeKey)
            .email(email)
            .gender(gender)
            .birthday(birthday)
            .build();
    }
}
