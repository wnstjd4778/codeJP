package com.spring.codejp.security.oauth2.user;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(String userNameAttributeName, Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get(userNameAttributeName);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId(); // 회원 고유 번호
    public abstract String getEmail(); // 회원 이메일
    public abstract String getName(); // 회원 이름
}
