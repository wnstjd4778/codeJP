package com.spring.codejp.security.oauth2.user;

import com.spring.codejp.user.domain.AuthProvider;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, String userNameAttributeName, Map<String,Object> attributes ) {

        if(registrationId.equalsIgnoreCase(AuthProvider.google.name())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if(registrationId.equalsIgnoreCase(AuthProvider.naver.name())) {
            return new NaverOAuth2UserInfo(userNameAttributeName, attributes);
        } else {
            throw new OAuth2AuthenticationException("현재 소셜로그인은 구글만 가능합니다.");
        }
    }
}
