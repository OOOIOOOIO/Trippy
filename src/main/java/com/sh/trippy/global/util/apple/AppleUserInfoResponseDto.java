package com.sh.trippy.global.util.apple;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AppleUserInfoResponseDto {
    @JsonProperty("sub")
    private String subject;

    @JsonProperty("email")
    private String email;

    @Builder
    public AppleUserInfoResponseDto(String subject, String email) {
        this.subject = subject;
        this.email = email;
    }
}
