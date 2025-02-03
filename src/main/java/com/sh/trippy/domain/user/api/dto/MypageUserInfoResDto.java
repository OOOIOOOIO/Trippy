package com.sh.trippy.domain.user.api.dto;

import com.sh.trippy.domain.user.domain.Users;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MypageUserInfoResDto {

    private String email;
    private String nickname;
    private String provider;
    private String motherLand;
    private String profileImg;

    public MypageUserInfoResDto(Users users) {
        this.email = users.getEmail();
        this.nickname = users.getNickname();
        this.provider = users.getProvider();
        this.motherLand = users.getMotherLand();
        this.profileImg = users.getProfileImg();
    }

}
