package com.sh.trippy.api.login;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTokenResDto {
    private String accessToken;
    private String refreshToken;
    private int existUser;

    public UserTokenResDto(String accessToken, String refreshToken, int existUser) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.existUser = existUser;
    }
}
