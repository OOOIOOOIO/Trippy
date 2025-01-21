package com.sh.trippy.domain.trip.api.dto.res;

import com.sh.trippy.domain.user.domain.Users;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripCompanionUserInfoResDto {
    private Long userId;
    private String nickname;
    private String profileImg;

    public TripCompanionUserInfoResDto(Users users) {
        this.userId = users.getUserId();
        this.nickname = users.getNickname();
        this.profileImg = users.getProfileImg();
    }
}
