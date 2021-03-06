package com.spring.codejp.config;

import com.spring.codejp.security.CustomUserDetailsService;
import com.spring.codejp.security.UserPrincipal;
import com.spring.codejp.security.jwt.JwtBasicAuthenticationFilter;
import com.spring.codejp.security.jwt.JwtCommonAuthorizationFilter;
import com.spring.codejp.security.jwt.JwtTokenProvider;
import com.spring.codejp.security.oauth2.CustomOAuth2UserService;
import com.spring.codejp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class securityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 인증할 때 사용할 userDetailsService의 password Encoder를 정의한다.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    // 다른 AuthorizationServer나 ResourceServer가 참조할 수 있도록 오버라이딩 해서 빈으로 등록
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 스프링 시큐리티 필터를 통해 모든 필러팅을 서버에서 처리
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .headers()
                    .frameOptions().sameOrigin() // Sockjs는 기본적으로 html, iframe 요소를 통한 전송을 허용하지 않도록 설정되는데 해당 내용을 해제한다.
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt으로 인증하므로 세션이 필요하지 않는다.
                    .and()
                .csrf().disable() // rest api이므로 csrf 보안이 필요하지 않는다.
                .httpBasic().disable() // rest api이므로 기본설정을 사용하지않는다. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트된다.
                .formLogin().disable()
                .addFilterBefore(new JwtBasicAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new JwtCommonAuthorizationFilter(authenticationManager(), jwtTokenProvider, userRepository))
                .authorizeRequests()
                    .antMatchers("/swagger-ui.html", "/swagger/**", "/swagger-resources/**", "/webjars/**", "/v2/api-docs").permitAll()
                    .antMatchers("/", "/**/signUp", "/**/signIn", "/user/**", "/login/**").permitAll()
                    .anyRequest().permitAll()
                    .and()
                .oauth2Login()// Oauth2 로그인 설정
                    .userInfoEndpoint() //
                        .userService(customOAuth2UserService)
                .and()
                    // oauth2 로그인후 jwt 토큰 생성
                    .successHandler(((request, response, authentication) -> {
                        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                        String accessToken = jwtTokenProvider.generateAccessToken(userPrincipal);
                        log.info(accessToken);
                        String refreshToken = jwtTokenProvider.generateRefreshToken(userPrincipal);
                        response.addHeader("Authorization", "Bearer " + accessToken);
                        response.setStatus(HttpServletResponse.SC_OK);
                    }))
                    .failureHandler(((request, response, exception) -> {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                    }));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
