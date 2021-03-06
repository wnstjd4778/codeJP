package com.spring.codejp.user.service;

import com.spring.codejp.user.domain.AuthProvider;
import com.spring.codejp.user.domain.User;
import com.spring.codejp.user.dto.UserSignInRequestDto;
import com.spring.codejp.user.dto.UserSignUpRequestDto;
import com.spring.codejp.user.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SMSService smsService;


    // 회원가입
    @Transactional
    public void insertUser(UserSignUpRequestDto requestDto) {
        User user = User.builder()
                .email(requestDto.getEmail())
                .tel(requestDto.getTel())
                .name(requestDto.getName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .snsType(AuthProvider.local)
                .answerCount(0)
                .submitCount(0)
                .build();
        userRepository.save(user);
    }

    // 중복 이메일인지 확인한다.
    public void checkDuplicateEmail(String email) throws NotFoundException {

        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            log.info("이메일 중복");
            throw new NotFoundException("현재 이메일을 사용할 수 없습니다.");
        }
    }

    // 중복 전화번호인지 확인한다.
    public void checkDuplicateTel(String tel) throws NotFoundException {

        Optional<User> user = userRepository.findByTel(tel);
        if(user.isPresent()) {
            throw new NotFoundException("현재 전화번호를 사용할 수 없습니다.");
        }
    }

    // 로그인
    @Transactional
    public void userSignIn(UserSignInRequestDto requestDto) throws NotFoundException {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new NotFoundException("로그인에 실패하였습니다."));


        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new NotFoundException("로그인에 실패하였습니다.");
        }
    }

    // 본인확인 인증번호를 핸드폰으로 전송
    public int validatePhone(String tel) {
        int validNum;
        do {
            validNum = (int) (Math.random() * 100000);
        } while (validNum < 10000);

        String message = "[인증번호]\n" + validNum;
        smsService.sendMessage(tel, message);
        return validNum;
    }

    // 내 회원 정보를 가져온다
    public User getMyInfo(String email) throws NotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        return user;
    }

}
