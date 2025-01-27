package com.sh.trippy.api.jwt.controller.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenIssueResDto {
    private String authorization;
    private String refreshToken;
    private int existUser;

    public TokenIssueResDto(String authorization, String refreshToken, int existUser) {
        this.authorization = authorization;
        this.refreshToken = refreshToken;
        this.existUser = existUser;
    }
}
