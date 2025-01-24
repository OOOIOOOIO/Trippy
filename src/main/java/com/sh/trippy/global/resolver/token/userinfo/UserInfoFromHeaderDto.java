package com.sh.trippy.global.resolver.token.userinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoFromHeaderDto {
    private Long userId;
    private String email;
    private String provider;
    private boolean paidFlag;

    public UserInfoFromHeaderDto(Long userId, String email, String provider, boolean paidFlag) {
        this.userId = userId;
        this.email = email;
        this.provider = provider;
        this.paidFlag = paidFlag;
    }
}
