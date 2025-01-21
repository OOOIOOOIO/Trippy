package com.sh.trippy.api.home.controller;

import com.sh.trippy.domain.user.api.dto.UserInfoResDto;
import com.sh.trippy.global.log.LogTrace;
import com.sh.trippy.global.resolver.token.userinfo.UserInfoFromHeader;
import com.sh.trippy.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Home Controller", description = "Trip API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {


    @Operation(
            summary = "홈 화면 조회 API",
            description = "홈 화면에 대해 상세 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "홈 화면 조회 성공 후, 홈 화면에 대한 정보(유저 정보, 여행통계, 예정된 여행, 다녀온 여행 정보) 리턴"
    )
    @LogTrace
    @GetMapping("")
    public ResponseEntity<UserInfoResDto> getHomeInfo(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
