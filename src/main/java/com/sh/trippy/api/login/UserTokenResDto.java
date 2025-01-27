package com.sh.trippy.api.login;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTokenResDto {
    private String authorization;
    private String refreshToken;
    private int existUser;

    public UserTokenResDto(String authorization, String refreshToken, int existUser) {
        this.authorization = authorization;
        this.refreshToken = refreshToken;
        this.existUser = existUser;
    }
}
