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

    // ????????? ??? ????????? userDetailsService??? password Encoder??? ????????????.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    // ?????? AuthorizationServer??? ResourceServer??? ????????? ??? ????????? ??????????????? ?????? ????????? ??????
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // ????????? ???????????? ????????? ?????? ?????? ???????????? ???????????? ??????
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .headers()
                    .frameOptions().sameOrigin() // Sockjs??? ??????????????? html, iframe ????????? ?????? ????????? ???????????? ????????? ??????????????? ?????? ????????? ????????????.
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt?????? ??????????????? ????????? ???????????? ?????????.
                    .and()
                .csrf().disable() // rest api????????? csrf ????????? ???????????? ?????????.
                .httpBasic().disable() // rest api????????? ??????????????? ?????????????????????. ??????????????? ???????????? ???????????? ???????????? ?????????????????????.
                .formLogin().disable()
                .addFilterBefore(new JwtBasicAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new JwtCommonAuthorizationFilter(authenticationManager(), jwtTokenProvider, userRepository))
                .authorizeRequests()
                    .antMatchers("/swagger-ui.html", "/swagger/**", "/swagger-resources/**", "/webjars/**", "/v2/api-docs").permitAll()
                    .antMatchers("/", "/**/signUp", "/**/signIn", "/user/**", "/login/**").permitAll()
                    .anyRequest().permitAll()
                    .and()
                .oauth2Login()// Oauth2 ????????? ??????
                    .userInfoEndpoint() //
                        .userService(customOAuth2UserService)
                .and()
                    // oauth2 ???????????? jwt ?????? ??????
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
