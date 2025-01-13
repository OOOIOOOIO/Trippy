package com.sh.trippy.domain.trip.api;

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
import org.springframework.web.bind.annotation.*;

@Tag(name = "Trip Controller", description = "Trip API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip")
public class TripController {

    @Operation(
            summary = "여행 상세 조회 API",
            description = "여행에 대해 상세 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행 상세 조회 성공 후, 여행 정보에 대해 리턴"
    )
    @LogTrace
    @GetMapping("")
    public ResponseEntity<UserInfoResDto> getTripInfo(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @Operation(
            summary = "여행 생성 API",
            description = "여행을 생성"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행 생성 성공 후, success(String) 리턴"
    )
    @LogTrace
    @PostMapping("")
    public String createTrip(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }


    @Operation(
            summary = "여행 수정 API",
            description = "여행 정보를 수정"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행 정보 수정 후, success(String) 리턴"
    )
    @LogTrace
    @PutMapping("/{tripId}")
    public String updateTrip(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }

    @Operation(
            summary = "여행 삭제 API",
            description = "여행을 삭제"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행 삭제 후, success(String) 리턴"
    )
    @LogTrace
    @DeleteMapping("/{tripId}")
    public String deleteTrip(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }



}
