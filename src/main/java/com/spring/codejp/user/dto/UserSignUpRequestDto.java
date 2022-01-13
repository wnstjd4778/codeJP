package com.spring.codejp.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserSignUpRequestDto {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 양식을 지켜주세요.")
    private String email; // 사용자 이메일

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password; //사용자 패스워드

    @NotBlank(message = "전화번호를 입력해주세요")
    private String tel;

    @NotBlank(message = "닉네임을 입력해주세요")
    private String name;

    public UserSignUpRequestDto() {

    };

    @Builder
    public UserSignUpRequestDto(String email, String password, String tel, String name) {
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.name = name;
    }
}