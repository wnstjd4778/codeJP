package com.spring.codejp.user.controller;

import com.spring.codejp.security.UserPrincipal;
import com.spring.codejp.security.jwt.JwtTokenProvider;
import com.spring.codejp.user.dto.AuthResponseDto;
import com.spring.codejp.user.dto.UserSignInRequestDto;
import com.spring.codejp.user.dto.UserSignUpRequestDto;
import com.spring.codejp.user.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signUp")
    public ResponseEntity<String> insertUser(@RequestBody UserSignUpRequestDto requestDto) throws NotFoundException {
        userService.checkDuplicateEmail(requestDto.getEmail());
        userService.checkDuplicateTel(requestDto.getTel());
        userService.insertUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<String> checkDuplicateEmail(@RequestParam @Email(message = "이메일 양식을 지켜주세요.") String email) throws NotFoundException {
        userService.checkDuplicateEmail(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/checkTel")
    public ResponseEntity<Integer> checkDuplicateTel(@RequestParam String tel) throws NotFoundException {
        userService.checkDuplicateTel(tel);
        int validNum = userService.validatePhone(tel);
        return ResponseEntity.status(HttpStatus.OK).body(validNum);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponseDto> userSignIn(@RequestBody UserSignInRequestDto requestDto) throws NotFoundException {
        userService.userSignIn(requestDto); // 에러를 잡기위해 로그인 정보를 2번 확인
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword()
        );
        Authentication authentication =
                authenticationManager.authenticate(usernamePasswordAuthenticationToken); // loadUserByName 호출
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // 토큰 생성
        String accessToken = jwtTokenProvider.generateAccessToken(userPrincipal);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userPrincipal);

        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDto(accessToken));
    }

}
