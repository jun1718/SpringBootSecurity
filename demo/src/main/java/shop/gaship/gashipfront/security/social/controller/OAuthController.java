package shop.gaship.gashipfront.security.social.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.domain.UserDetailsDto;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.service.NaverLoginService;

@Controller
@RequestMapping("/securities")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {
    private final NaverLoginService naverLoginServiceImpl;

    @GetMapping("/login/naver")
    @ResponseBody
    public ResponseEntity<String> redirectUriForLoginPageRequestNaver(HttpServletResponse response) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        String uriForLoginPageRequest = naverLoginServiceImpl.getUriForLoginPageRequest();
        headers.setLocation(URI.create(uriForLoginPageRequest));
        log.debug("uriForLoginPageRequest : {}", uriForLoginPageRequest);

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping(value = "/login/naver/callback", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAccessTokenAndAuthenticateNaver(String code, String state) throws Exception {
        NaverAccessToken naverAccessToken = naverLoginServiceImpl.getAccessToken(code, state);
        NaverUserData data = naverLoginServiceImpl.getUserDataThroughAccessToken(naverAccessToken.getAccessToken());
        Member member = naverLoginServiceImpl.getMember(data.getResponse().getEmail());
        List<String> authorities = new ArrayList<>();
        authorities.add("ROLE_" + "USER");
        member.setAuthorities(authorities);
        member.setPassword("1234");

        UserDetailsDto userDetailsDto = new UserDetailsDto("member.getEmail()", member.getPassword(), member.getAuthorities().stream()
            .map(i -> new SimpleGrantedAuthority(i))
            .collect(Collectors.toList()), member);

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsDto, null,
            List.of(new SimpleGrantedAuthority("ROLE_USER")));
        context.setAuthentication(authentication);
        return "/all";
    }



}
