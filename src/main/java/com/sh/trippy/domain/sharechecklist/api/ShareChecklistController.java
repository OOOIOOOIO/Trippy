package com.sh.trippy.domain.sharechecklist.api;

import com.sh.trippy.global.log.LogTrace;
import com.sh.trippy.global.resolver.token.userinfo.UserInfoFromHeader;
import com.sh.trippy.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Trip Share Checklist Controller", description = "Trip Share Checklist API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip/checklist")
public class ShareChecklistController {

    @Operation(
            summary = "여행에 대한 공유 체크리스트 조회 API",
            description = "여행에 대한 공유 체크리스트 리스트로 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행에 대한 공유 체크리스트 조회 성공 후, 체크리스트 정보 리스트로 리턴"
    )
    @LogTrace
    @GetMapping("/{tripId}")
    public String getTripReply(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }


    @Operation(
            summary = "여행에 대한 공유 체크리스트 생성 API",
            description = "여행에 대한 공유 체크리스트 생성"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행에 대한 공유 체크리스트 생성 성공 후, success(String) 리턴"
    )
    @LogTrace
    @PostMapping("/{tripId}")
    public String createTripReply(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }


    @Operation(
            summary = "여행에 대한 공유 체크리스트 수정 API",
            description = "여행에 대한 공유 체크리스트 수정"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행에 대한 공유 체크리스트 수정 성공 후, success(String) 리턴"
    )
    @LogTrace
    @PutMapping("/{tripId}/{shId}")
    public String updateTripReply(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }


    @Operation(
            summary = "여행에 대한 공유 체크리스트 삭제 API",
            description = "여행에 대한 공유 체크리스트 삭제"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행에 대한 공유 체크리스트 삭제 성공 후, success(String) 리턴"
    )
    @LogTrace
    @DeleteMapping("/{tripId}/{shId}")
    public String deleteTripReply(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }


    @Operation(
            summary = "여행에 대한 공유 체크리스트 성공여부 API",
            description = "여행에 대한 공유 체크리스트 성공여부 수정"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행에 대한 공유 체크리스트 성공여부 수정 성공 후, success(String) 리턴"
    )
    @LogTrace
    @PatchMapping("/{tripId}/checklist/{shId}")
    public String changeCompletedFlag(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }



}
