package com.sh.trippy.api.login.apple.controller.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class AppleUserInfoResponseDto {
//    @JsonProperty("sub")
    private String subject;

//    @JsonProperty("email")
    private String email;
    private String refreshToken;
    private String provider;

    @Builder
    public AppleUserInfoResponseDto(String subject, String email, String refreshToken, String provider) {
        this.subject = subject;
        this.email = email;
        this.refreshToken = refreshToken;
        this.provider = provider;
    }
}
