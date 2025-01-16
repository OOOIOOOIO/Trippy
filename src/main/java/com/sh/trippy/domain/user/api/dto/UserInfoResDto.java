package com.sh.trippy.domain.user.api.dto;

import com.sh.trippy.domain.user.domain.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.UrlResource;

@Getter
@NoArgsConstructor
public class UserInfoResDto {

    private String email;
    private String nickname;
    private String provider;
    private String motherLand;
    private UrlResource profileImg;

    public UserInfoResDto(Users users, UrlResource profileImg) {
        this.email = users.getEmail();
        this.nickname = users.getNickname();
        this.provider = users.getProvider();
        this.motherLand = users.getMotherLand();
        this.profileImg = profileImg;
    }

    public void setProfileImg(UrlResource profileImg){
        this.profileImg = profileImg;
    }
}
