package shop.gaship.gashipfront.security.social.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.social.adapter.Adapter;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.exception.ReceiveDataException;

@Service
@RequiredArgsConstructor
public class NaverLoginServiceImpl implements NaverLoginService {
    @Value("${naver-client-id}")
    private String clientId;

    @Value("${naver-client-secret}")
    private String clientSecret;

    @Value("${naver-redirect-url}")
    private String redirectUrl;

    @Value("${naver-api-url-login}")
    private String apiUrlForLogin;

    @Value("${naver-api-url-accesstoken}")
    private String apiUrlForAccessToken;

    @Value("${naver-api-url-userdata}")
    private String apiUrlForUserData;

    // TODO 2 : 동시접속시 state값 redis 관리할때 키값방향성sessionId? 그럼 다른서버에서는 ? jsessionId? 얻어오는법? (nave front단에서는 session으로 관리..)
    private BigInteger state;

    private final Adapter adapter;

    @Override
    public String getUriForLoginPageRequest() throws UnsupportedEncodingException {
        state = new BigInteger(130, new SecureRandom());
        // TODO 1 : 동기화여부확인 필요 메서드안이니까 필요 없지않나?
        StringBuilder uriForLoginPageRequest = new StringBuilder();
        uriForLoginPageRequest
            .append(apiUrlForLogin)
            .append("&client_id=").append(clientId)
            .append("&redirect_uri=").append(URLEncoder.encode(redirectUrl, "UTF-8"))
            .append("&state=").append(state.toString());

        return uriForLoginPageRequest.toString();
    }

    @Override
    public NaverAccessToken getAccessToken(String code, String state) {
        if (!Objects.equals(state, this.state.toString())) throw new RuntimeException("csrf protect");
        // TODO 3 : 내가 이해한 github 이상의 csrf 방어를 위해 넣은부분이 맞는지?

        StringBuilder uriForAccessToken = new StringBuilder();
        uriForAccessToken
            .append(apiUrlForAccessToken)
            .append("&client_id=").append(clientId)
            .append("&client_secret=").append(clientSecret)
            .append("&code=").append(code);
//            .append("&state=").append(this.state);

        return adapter.requestNaverAccessToken(uriForAccessToken.toString());
    }

    @Override
    public NaverUserData getUserDataThroughAccessToken(String accessToken) {
        NaverUserData data = adapter.requestNaverUserData(apiUrlForUserData, accessToken);
        if (!Objects.equals(data.getMessage(), "success")) throw new ReceiveDataException("message : " + data.getMessage());
        return data;
    }

    @Override
    public Member getMember(String mobile) {
        return adapter.requestMemberByMobile(mobile);
    }

    @Override
    public String requestJWT(String id, String email) throws Exception {
        return null;
    }
}
