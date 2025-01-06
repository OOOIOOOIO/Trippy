package com.sh.trippy.domain.plan.api;

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

@Tag(name = "Trip Plan Controller", description = "Trip Plan API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip")
public class PlanController {

    @Operation(
            summary = "해당 일자의 여행 계획 조회 API",
            description = "해당 일자의 여행에 대한 계획 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "해당 일자의 여행 게획 조회 성공 후, 여행 계획 리스트로 리턴"
    )
    @LogTrace
    @PostMapping("/{tripId}/plan/{planDate}")
    public String getTripPlan(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }


    @Operation(
            summary = "여행 계획 생성 API",
            description = "여행에 대한 계획 생성"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행에 대한 게획 생성 성공 후, success(String) 리턴"
    )
    @LogTrace
    @PostMapping("/{tripId}/plan")
    public String createTripPlan(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }

    @Operation(
            summary = "여행 계획 수정 API",
            description = "여행에 대한 계획 수정"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행에 대한 게획 수정 성공 후, success(String) 리턴"
    )
    @LogTrace
    @PutMapping("/{tripId}/plan/{planId}")
    public String updateTripPlan(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }

    @Operation(
            summary = "여행 계획 삭제 API",
            description = "여행에 대한 계획 삭제"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행에 대한 게획 삭제 성공 후, success(String) 리턴"
    )
    @LogTrace
    @DeleteMapping("/{tripId}/plan/{planId}")
    public String deleteTripPlan(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }



}
