package com.sh.trippy.api.login.apple.controller.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AppleResponseInfoResDto {
//    @JsonProperty("sub")
    private String subject;

//    @JsonProperty("email")
    private String email;
    private String refreshToken;
    private String provider;

    @Builder
    public AppleResponseInfoResDto(String subject, String email, String refreshToken, String provider) {
        this.subject = subject;
        this.email = email;
        this.refreshToken = refreshToken;
        this.provider = provider;
    }
}
