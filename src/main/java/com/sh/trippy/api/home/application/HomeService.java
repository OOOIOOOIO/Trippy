package com.sh.trippy.api.home.application;

import com.sh.trippy.api.home.controller.dto.HomeResDto;
import com.sh.trippy.api.home.controller.dto.HomeTripInfoResDto;
import com.sh.trippy.api.home.controller.dto.HomeTripStatusResDto;
import com.sh.trippy.api.home.controller.dto.HomeUserInfoResDto;
import com.sh.trippy.domain.trip.application.TripService;
import com.sh.trippy.domain.user.application.UsersService;
import com.sh.trippy.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HomeService {

    private final TripService tripService;
    private final UsersService usersService;

    public HomeResDto getHomeInfo(UserInfoFromHeaderDto userInfoFromHeaderDto){

        // 유저 정보
        HomeUserInfoResDto homeUserInfoResDto = usersService.getUserInfo(userInfoFromHeaderDto);

        // 예정된 여행, 다여온 여행
        List<HomeTripInfoResDto> homeTripInfoResDtoList = tripService.getTripList(userInfoFromHeaderDto);

        // 여행 통계
        HomeTripStatusResDto homeTripStatusResDto = getTripStatus(homeTripInfoResDtoList);

        return new HomeResDto(homeTripInfoResDtoList, homeTripStatusResDto, homeUserInfoResDto);
    }



    private HomeTripStatusResDto getTripStatus(List<HomeTripInfoResDto> homeTripInfoResDtoList){

        HomeTripStatusResDto homeTripStatusResDto = new HomeTripStatusResDto(0, 0);
        for(HomeTripInfoResDto homeTripInfoResDto : homeTripInfoResDtoList){
            // 국내, 해외
            if (homeTripInfoResDto.isAbroadFlag()) {
                homeTripStatusResDto.addAbroad();
            }
            else{
                homeTripStatusResDto.addDomestic();
            }
        }

        return homeTripStatusResDto;

    }

}
