package com.sh.trippy.api.home.application;

import com.sh.trippy.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HomeService {

    public void getHomeInfo(UserInfoFromHeaderDto userInfoFromHeaderDto){

        // 유저 정보

        // 여행 통계

        // 예정된 여행, 다여온 여행

    }
}
