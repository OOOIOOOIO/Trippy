package com.sh.trippy.api.login.apple.application;

import com.sh.trippy.api.jwt.application.TokenService;
import com.sh.trippy.api.login.UserTokenResDto;
import com.sh.trippy.api.login.apple.controller.dto.AppleResponseInfoResDto;
import com.sh.trippy.domain.user.application.UsersService;
import com.sh.trippy.global.jwt.JwtUtils;
import com.sh.trippy.global.log.LogTrace;
import com.sh.trippy.global.util.apple.AppleLoginClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AppleLoginService {

    private final AppleLoginClient appleLoginClient;
    private final UsersService usersService;

    private final JwtUtils jwtUtils;
    private final TokenService tokenService;

    @LogTrace
    public UserTokenResDto appleLogin(String authorizationCode) throws IOException {

        // apple 로그인 및 DB저장
        AppleResponseInfoResDto appleResponseInfoResDto = appleLoginClient.getAppleUserProfile(authorizationCode);

        String[] savedUserInfo = usersService.saveAppleUser(appleResponseInfoResDto).split(",");
        Long userId = Long.parseLong(savedUserInfo[0]);
        boolean paidFlag = savedUserInfo[1].equals("paid") ? true : false;
        int existUser = savedUserInfo[2].equals("exist") ? 1 : -1;

        // token 발급, 첫로그인은
        String jwtAccessToken = jwtUtils.generateAccessToken(userId, appleResponseInfoResDto.getEmail(), appleResponseInfoResDto.getProvider(), paidFlag);
        String jwtRefreshToken = jwtUtils.generateRefreshToken(userId, appleResponseInfoResDto.getEmail(), appleResponseInfoResDto.getProvider(), paidFlag);

        // redis 저장
//        tokenService.uploadAccessTokenToRedis(jwtAccessToken, userId);
//        tokenService.uploadRefreshTokenToRedis(jwtRefreshToken, userId);


        return new UserTokenResDto(jwtAccessToken, jwtRefreshToken, existUser);

    }

    @LogTrace
    public void appleLogout(Long userId){
        String refreshToken = usersService.getAppleUserRefreshToken(userId);

        appleLoginClient.logoutAppleAccount(refreshToken);

    }


}
