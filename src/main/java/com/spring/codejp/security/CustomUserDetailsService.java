package com.spring.codejp.security;

import com.spring.codejp.user.domain.User;
import com.spring.codejp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // authenticationManager에서 authentication함수를 통해 호출
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUserName 호출");

        // 입력된 아이디로 db에서 해당하는 아이디 정보를 찾는다.
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일을 찾을 수 없습니다."));

        //db에사 가져온 User 정보를 UserDetail 클래스로 만들어 전달한다.
        return UserPrincipal.create(user);
    }
}
