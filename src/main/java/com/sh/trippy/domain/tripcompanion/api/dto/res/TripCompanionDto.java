package com.sh.trippy.domain.tripcompanion.api.dto.res;

import com.sh.trippy.domain.tripcompanion.domain.model.TripCompanion;
import com.sh.trippy.domain.tripcompanion.domain.model.TripRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripCompanionDto {

    private Long companionId;
    private TripRole tripRole;
    private String nickname;
    private String profileImg;
    private Long userId;

    public TripCompanionDto(TripCompanion tripCompanion) {
        this.companionId = tripCompanion.getCompanionId();
        this.tripRole = tripCompanion.getTripRole();
        this.userId = tripCompanion.getUsers().getUserId();
        this.nickname = tripCompanion.getUsers().getNickname();
        this.profileImg = tripCompanion.getUsers().getProfileImg();
    }
}
