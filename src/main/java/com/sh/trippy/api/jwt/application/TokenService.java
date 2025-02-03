package com.sh.trippy.api.jwt.application;

import com.sh.trippy.global.jwt.JwtInfoProperties;
import com.sh.trippy.global.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sh.trippy.global.util.redis.RedisConst.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final RedisUtil redisUtil;
    private final JwtInfoProperties jwtInfoProperties;


    /**
     * redis에 token(access, refresh) 저장
     */
    public void uploadAccessTokenToRedis(String token, Long userId){
        redisUtil.putString(ACCESS_TOKEN.prefix() + userId, token, jwtInfoProperties.getAccessTokenExpireMin());

        System.out.println();
    }

    public void uploadRefreshTokenToRedis(String token, Long userId){
        redisUtil.putString(REFRESH_TOKEN.prefix() + userId, token, jwtInfoProperties.getRefreshTokenExpireMin());

        System.out.println();
    }


    /**
     * redis에서 token(access, refresh) 가져오기
     */
    public String getTokenFromRedis(String prefix){
        return redisUtil.getByClassType(prefix, String.class);
    }


    /**
     * redis에서 token 삭제
     */
    public void deleteTokenFromRedis(String accessPrefix, String refreshPrefix){
        redisUtil.deleteKey(accessPrefix);
        redisUtil.deleteKey(refreshPrefix);
    }




}
