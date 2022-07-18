package shop.gaship.gashipfront.security.social.adapter;


import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.dto.OAuth2Attributes;

public interface Adapter {
    // TODO 4 : refactoring필요 확장성고려 하나의 메서드로 만들기
    NaverAccessToken requestNaverAccessToken(String uri);
    OAuth2Attributes requestPaycoAccessToken(String uri);

    NaverUserData requestNaverUserData(String apiUrlForUserData, String accessToken);
    void requestPaycoInfo();

    Member requestMemberByMobile(String mobile);
}
