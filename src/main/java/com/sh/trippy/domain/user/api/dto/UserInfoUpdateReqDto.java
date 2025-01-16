package com.sh.trippy.domain.user.api.dto;


import com.sh.trippy.domain.user.domain.Users;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.UrlResource;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoUpdateReqDto {

    private String nickname;
    private String profileImg;
    private String motherLand;


}
