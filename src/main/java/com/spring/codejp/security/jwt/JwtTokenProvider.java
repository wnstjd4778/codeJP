package com.spring.codejp.security.jwt;

import com.spring.codejp.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
public class JwtTokenProvider {

    private String accessTokenSecret = "토레스";
    private String refreshTokenSecret = "페르난도";

    private static final long ACCESS_TOKEN_EXPIRED_MSC = 1000L * 60 * 60 * 24 * 30; // 1시간
    private static final long REFRESH_TOKEN_EXPIRED_MSC = 1000L * 60 * 60 * 24 * 14; // 2주

    private static final String HEDAER_NAME = "Authorization";

    public enum TokenType {ACCESS_TOKEN, REFRESH_TOKEN};

    // 처음 시작할 때 실행
    @PostConstruct
    protected void init() {
        accessTokenSecret = Base64.getEncoder().encodeToString(accessTokenSecret.getBytes());
        refreshTokenSecret = Base64.getEncoder().encodeToString(refreshTokenSecret.getBytes());
    }

    // AccessToken 발급
    public String generateAccessToken(UserPrincipal userPrincipal) {
        return createToken(userPrincipal.getUsername(), TokenType.ACCESS_TOKEN);
    }

    // RefreshToken 발급
    public String generateRefreshToken(UserPrincipal userPrincipal) {
        return createToken(userPrincipal.getUsername(), TokenType.REFRESH_TOKEN);
    }

    // 인증된 유저의 authentication에서 userPrincipal을 추출해 token을 생성
    public String createToken(String principal, TokenType tokenType) {

        String secretKey;
        long exprireTime;

        if(tokenType == TokenType.ACCESS_TOKEN) {
            secretKey = accessTokenSecret;
            exprireTime = ACCESS_TOKEN_EXPIRED_MSC;
        } else {
            secretKey = refreshTokenSecret;
            exprireTime = REFRESH_TOKEN_EXPIRED_MSC;
        }

        //토큰 생성
        Claims claims = Jwts.claims().setSubject(principal); // 데이터
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발급 일자
                .setExpiration(new Date(now.getTime() + exprireTime)) // 토큰 만료시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 토큰암호화 알고리즘
                .compact();
    }

    public boolean validateToken(String token) {
        String secretKey = accessTokenSecret;

        try {
            log.debug("validateToken's secretKey : " + secretKey);

            Jws<Claims> claimsJws = Jwts.parser() // jwt을 파싱해서 데이터를 얻음
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            boolean isNotExpire = !claimsJws.getBody().getExpiration().before(new Date()); // 만료되면 false를 반환
            return isNotExpire;
        } catch (Exception e) {
            return false;
        }
    }

    // Jwt 토큰에서 회원 이메일 또는 관리자 아이디 추출
    public String getPrincipal(String token, TokenType tokenType) {
        String secretKey;

        if(tokenType == TokenType.ACCESS_TOKEN) {
            secretKey = accessTokenSecret;
        } else {
            secretKey = refreshTokenSecret;
        }
        return  Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
