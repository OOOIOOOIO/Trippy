package com.sh.trippy.api.home.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeResDto {
    private List<HomeTripInfoResDto> homeTripInfoResDtoList;
    private HomeTripStatusResDto homeTripStatusResDto;
    private HomeUserInfoResDto homeUserInfoResDto;

    public HomeResDto(List<HomeTripInfoResDto> homeTripInfoResDtoList, HomeTripStatusResDto homeTripStatusResDto, HomeUserInfoResDto homeUserInfoResDto) {
        this.homeTripInfoResDtoList = homeTripInfoResDtoList;
        this.homeTripStatusResDto = homeTripStatusResDto;
        this.homeUserInfoResDto = homeUserInfoResDto;
    }
}
