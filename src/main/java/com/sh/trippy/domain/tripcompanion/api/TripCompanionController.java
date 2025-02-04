package com.sh.trippy.domain.tripcompanion.api;

import com.sh.trippy.domain.tripcompanion.api.dto.res.TripCompanionResDto;
import com.sh.trippy.domain.tripcompanion.application.TripCompanionService;
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

@Tag(name = "Trip Companion Controller", description = "Trip Companion API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip/companion")
public class TripCompanionController {


    private final TripCompanionService tripCompanionService;

    /**
     * 해당 여행 동반인들 전체 조회
     */
    @Operation(
            summary = "해당 여행 동반인들 전체 조회 API",
            description = "해당 여행 동반인들 전체 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "해당 여행 동반인들 전체 조회 후 동반인 정보가 담긴 리스트 리턴"
    )
    @LogTrace
    @GetMapping("/{tripId}")
    public ResponseEntity<TripCompanionResDto> getCompanionList(@PathVariable(name = "tripId") Long tripId){

        TripCompanionResDto companionList = tripCompanionService.getCompanionList(tripId);

        return new ResponseEntity<>(companionList, HttpStatus.OK);
    }

    /**
     * (Guest가 Host의 초대 수락하기 후) 동반자 추가
     */
    @Operation(
            summary = "동반자 추가 API",
            description = "Guest가 Host의 초대 수락 후 동반자 추가"
    )
    @ApiResponse(
            responseCode = "200",
            description = "동반자 추가 후 success 리턴"
    )
    @LogTrace
    @PostMapping("/{tripId}")
    public ResponseEntity<String> addCompanion(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto,
                                               @PathVariable(name = "tripId") Long tripId){

        tripCompanionService.addCompanion(userInfoFromHeaderDto, tripId);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }



    /**
     * 호스트가 동반자 퇴출시키기 & 게스트가 스스로 탈퇴하기
     */
    @Operation(
            summary = "호스트가 동반자 퇴출시키기 & 게스트가 스스로 탈퇴하기 API",
            description = "호스트가 동반자 퇴출시키기 & 게스트가 스스로 탈퇴하기"
    )
    @ApiResponse(
            responseCode = "200",
            description = "호스트가 동반자 퇴출시키기 & 게스트가 스스로 탈퇴하기 후 success 리턴"
    )
    @LogTrace
    @DeleteMapping("/{companionId}")
    public ResponseEntity<String> quitCompanion(@PathVariable(name = "companionId") Long companionId){

        tripCompanionService.quitCompanion(companionId);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }








}
