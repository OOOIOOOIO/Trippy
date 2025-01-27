package com.sh.trippy.token;

import com.sh.trippy.api.jwt.application.TokenService;
import com.sh.trippy.global.common.RedisConst;
import com.sh.trippy.global.jwt.JwtUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenIssueTest {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    TokenService tokenService;

    @Test
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

        Assertions.assertThat(jwtAccessToken).isEqualTo(accessToken);
        Assertions.assertThat(jwtRefreshToken).isEqualTo(refreshToken);


    }
    

}
