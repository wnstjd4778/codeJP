package com.spring.codejp.security.oauth2;

import com.spring.codejp.security.UserPrincipal;
import com.spring.codejp.security.oauth2.user.OAuth2UserInfo;
import com.spring.codejp.security.oauth2.user.OAuth2UserInfoFactory;
import com.spring.codejp.user.domain.AuthProvider;
import com.spring.codejp.user.domain.User;
import com.spring.codejp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    // 동일 회원 유무 확인
    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

        // 현재 진행중인 서비스를 구분하는 코드
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        // 로그인 진행시 키가되는 pk값
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2UserInfo oAuth2UserInfo =
                OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        if(oAuth2UserInfo.getEmail() == null || oAuth2UserInfo.getEmail() == "") {
            throw new SessionAuthenticationException("회원 정보에 이메일이 존재하지 않습니다.");
        }

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;

        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getSnsType().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationException("현재 계정은 이미 등록된 계정입니다.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    // 소셜 로그인 회원가입
    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = User.builder()
                .snsType(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .snsKey(oAuth2UserInfo.getId())
                .email(oAuth2UserInfo.getEmail())
                .name(oAuth2UserInfo.getName())
                .build();
        return userRepository.save(user);
    }

    // 기존 유저 정보 수정
    private User updateExistingUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setName(oAuth2UserInfo.getName());
        return userRepository.save(user);
    }
}
