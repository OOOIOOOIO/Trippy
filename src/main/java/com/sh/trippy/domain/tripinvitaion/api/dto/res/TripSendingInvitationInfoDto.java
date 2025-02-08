package com.sh.trippy.domain.tripinvitaion.api.dto.res;

import com.sh.trippy.domain.tripinvitaion.domain.model.InvitationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripSendingInvitationInfoDto {

    private String nickname;
    private String profileImg;
    private InvitationStatus invitationStatus;


}
