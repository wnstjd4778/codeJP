package com.spring.codejp.user.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class AuthResponseDto {

    private String accessToken;
    private String tokenType = "Bearer";

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
