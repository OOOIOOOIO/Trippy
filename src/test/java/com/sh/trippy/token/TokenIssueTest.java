package com.sh.trippy.token;

import com.sh.trippy.api.jwt.application.TokenService;
import com.sh.trippy.global.common.RedisConst;
import com.sh.trippy.global.jwt.JwtClaimDto;
import com.sh.trippy.global.jwt.JwtUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenIssueTest {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    TokenService tokenService;

    @Test
    @Transactional
    @DisplayName(value = "토큰 발급 테스트")
    public void issueTokenTest(){
        // given
        Long userId = 1L;
        String email = "bcnhg6z84g@privaterelay.appleid.com";
        String provider = "apple";
        boolean paidFlag = false;

        // when

        // token 발급
        String jwtAccessToken = jwtUtils.generateAccessToken(userId, email, provider, paidFlag);
        String jwtRefreshToken = jwtUtils.generateRefreshToken(userId, email, provider, paidFlag);

        System.out.println("jwtAccessToken = " + jwtAccessToken);
        System.out.println("jwtRefreshToken = " + jwtRefreshToken);

        System.out.println();
        System.out.println();

        // redis 저장
        tokenService.uploadAccessTokenToRedis(jwtAccessToken, userId);
        tokenService.uploadRefreshTokenToRedis(jwtRefreshToken, userId);

        // then
        String accessToken = tokenService.getTokenFromRedis(RedisConst.ACCESS_TOKEN.prefix()+userId);
        String refreshToken = tokenService.getTokenFromRedis(RedisConst.REFRESH_TOKEN.prefix()+userId);

        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);

        assertThat(jwtAccessToken).isEqualTo(accessToken);
        assertThat(jwtRefreshToken).isEqualTo(refreshToken);


    }

    @Test
    public void getClaimFromExpiredJwtToken(){
        // given
        String expiredToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiY25oZzZ6ODRnQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsInVzZXJJbmZvIjp7InVzZXJJZCI6MSwiZW1haWwiOiJiY25oZzZ6ODRnQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsInByb3ZpZGVyIjoiYXBwbGUiLCJ0b2tlblR5cGUiOiJyZWZyZXNoX3Rva2VuIiwicGFpZEZsYWciOmZhbHNlfSwiaWF0IjoxNzM3OTYxNzkzLCJleHAiOjE3NDIxOTUzOTN9.5AEH3WrLsuzgBlVJguv9HJ7TYEENF2zf7lAsw4fWI6Y";
        // when
        JwtClaimDto claimFromToken = jwtUtils.getClaimFromToken(expiredToken);

        String email = claimFromToken.getEmail();
        // then
        System.out.println("email = " + email);


    }
    

}
