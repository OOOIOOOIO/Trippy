package com.sh.trippy.global.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtClaimDto {

    private Long userId;
    private String email;
    private String provider;
    private String tokenType;
    private boolean paidFlag;


    public JwtClaimDto(Long userId, String email, String provider, String tokenType, boolean paidFlag) {
        this.userId = userId;
        this.email = email;
        this.provider = provider;
        this.tokenType = tokenType;
        this.paidFlag = paidFlag;
    }

}
