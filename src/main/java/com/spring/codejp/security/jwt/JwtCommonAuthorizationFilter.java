package com.spring.codejp.security.jwt;

// 모든 요청의 Http header에 jwt가 있는지 확인 하고 있으면 인증을 해준다.

import com.spring.codejp.security.UserPrincipal;
import com.spring.codejp.user.domain.User;
import com.spring.codejp.user.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JwtCommonAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    public JwtCommonAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        log.info("jwt토큰 이름 : " + header);
        // 헤더가 있는지 확인하고 없으면 빠져나감
        if(header == null || header == "" || !header.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        // 토큰 인증을 위한 전처리
        HttpServletRequestWrapper myRequest = new HttpServletRequestWrapper((HttpServletRequest) request) {
            @Override
            public String getHeader(String name) {
                if(name.equals("Authorization")) {
                    String basic = request.getHeader("Authorization").replace("Bearer ", "");
                    return basic;
                }
                return super.getHeader(name);
            }
        };

        //만약 header가 존재한다면, DB로부터 user의 권한을 확인하고, authorization을 수행한다.
        Authentication authentication =
                getUsernamePasswordAuthentication(myRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(myRequest, response); // filter 수행 계속함
    }

    // 헤더의 jwt token 내부에 있는 정보를 통해 DB와 일치하는 유저를 찾아 인증을 완료한다. (Userprincipal 객체 생성)
    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String principal = null;
        UserPrincipal userPrincipal = null;


        try {
            principal = jwtTokenProvider.getPrincipal(token, JwtTokenProvider.TokenType.ACCESS_TOKEN);

            Optional<User> oUser = userRepository.findByEmail(principal);
            User user = oUser.get();
            userPrincipal = UserPrincipal.create(user);

            // OAuth인지 일반 로그인인지 구분할 필요가 없다. 왜냐하면 password를 Authentication이 가질 필요가 없기 때문이다.
            // JWT가 로그인 프로세스를 가로채서 인증을 다 해준다. (OAUTH2 일반로그인 둘다)

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        }
        return null;
    }

    // request에서 header(jwt)를 획득 후, 해당 유저를 db에서 찾아 인증을 진행(authentication 생성)
}
