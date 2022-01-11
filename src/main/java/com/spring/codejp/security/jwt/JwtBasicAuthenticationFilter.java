package com.spring.codejp.security.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.codejp.security.CustomUserDetailsService;
import com.spring.codejp.security.UserPrincipal;
import com.spring.codejp.user.domain.User;
import com.spring.codejp.user.dto.UserSignInRequestDto;
import com.spring.codejp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/*  로그인 할 때 호출. id/pw를 통해 UsernamePasswordAuthenticationToken을 생성한후
    AuthenticationManager가 AuthentocationProvider를 순회하며 인증 가능 여부를 체크한다.
    이 때, AuthenticationProvider는 UserDetailsService의 loadByName을 통해 사용자 정보를 조회하고
    비밀번호 일치여부를 확인하고 인증을 해준다. 해당 authentication 객체를 SecurityContextHolder에
    저장한 후 반환한다. */
public class JwtBasicAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // 로그인 정보를 캐치한후 인증을 시도한다.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserSignInRequestDto userSignInRequestDto = null;
        try {
            userSignInRequestDto = new ObjectMapper().readValue(request.getInputStream(), UserSignInRequestDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Optional<User> user = userRepository.findByEmail(userSignInRequestDto.getEmail());
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.get().getEmail());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userSignInRequestDto.getPassword()
        );
        Authentication authentication =
                authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    // 인증이 성공하면 response의 header에 jwt토큰을 넣어서 반환한다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authResult.getPrincipal();
        String accessToken = jwtTokenProvider.generateAccessToken(userPrincipal);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userPrincipal);
        response.addHeader("Authorization", "Bearer " + accessToken);
    }
}
