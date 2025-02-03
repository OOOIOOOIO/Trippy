package com.sh.trippy.domain.plan.api;

import com.sh.trippy.domain.plan.api.dto.req.PlanCreateReqDto;
import com.sh.trippy.domain.plan.api.dto.req.PlanUpdateReqDto;
import com.sh.trippy.domain.plan.api.dto.res.PlanInfoResDto;
import com.sh.trippy.domain.plan.application.PlanService;
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

import java.time.LocalDate;

@Tag(name = "Trip Plan Controller", description = "Trip Plan API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip")
public class PlanController {

    private final PlanService planService;

    @Operation(
            summary = "해당 일자의 여행 계획 조회 API",
            description = "해당 일자의 여행에 대한 계획 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "해당 일자의 여행 게획 조회 성공 후, 여행 계획 리스트로 리턴"
    )
    @LogTrace
    @GetMapping("/{tripId}/plan")
    public ResponseEntity<PlanInfoResDto> getTripPlan(@PathVariable(name = "tripId") Long tripId,
                              @RequestParam(name = "tripDate") LocalDate tripDate){

        PlanInfoResDto tripPlan = planService.getTripPlan(tripId, tripDate);

        return new ResponseEntity<>(tripPlan, HttpStatus.OK);
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
    public ResponseEntity<String> createTripPlan(@PathVariable(name = "tripId") Long tripId,
                                 @RequestBody PlanCreateReqDto planCreateReqDto){
        planService.createTripPlan(tripId, planCreateReqDto);


        return new ResponseEntity<>("success", HttpStatus.OK);
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
    @PutMapping("/plan/{planId}")
    public ResponseEntity<String> updateTripPlan(@PathVariable(name = "planId") Long planId,
                                 @RequestBody PlanUpdateReqDto planUpdateReqDto){

        planService.updateTripPlan(planId, planUpdateReqDto);

        return new ResponseEntity<>("success", HttpStatus.OK);
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
    @DeleteMapping("/plan/{planId}")
    public ResponseEntity<String> deleteTripPlan(@PathVariable(name = "planId") Long planId){

        planService.deleteTripPlan(planId);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }



    @Operation(
            summary = "여행 계획 완료여부 설정 API",
            description = "여행에 대한 계획 완료여부 설정"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행에 대한 게획의 완료 여부 설정 후, success(String) 리턴"
    )
    @LogTrace
    @PatchMapping("/plan/{planId}")
    public ResponseEntity<String> updateCompleteStatus(@PathVariable(name = "planId") Long planId){

        planService.updateCompleteStatus(planId);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }



}
