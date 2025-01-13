package com.sh.trippy.domain.reply.api;


import com.sh.trippy.global.log.LogTrace;
import com.sh.trippy.global.resolver.token.userinfo.UserInfoFromHeader;
import com.sh.trippy.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Trip Reply Controller", description = "Trip Reply API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip")
public class ReplyController {


    @Operation(
            summary = "여행에 대한 댓글 조회 API",
            description = "여행에 대한 조회 생성"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행에 대해 댓글 조회 성공 후, 댓글 리스트로 리턴"
    )
    @LogTrace
    @GetMapping("/{tripId}/reply")
    public String getTripReply(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }


    @Operation(
            summary = "여행에 대한 댓글 생성 API",
            description = "여행에 대한 댓글 생성"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행에 대해 댓글 생성 성공 후, success(String) 리턴"
    )
    @LogTrace
    @PostMapping("/{tripId}/reply")
    public String createTripReply(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }


    @Operation(
            summary = "여행에 대한 댓글 수정 API",
            description = "여행에 대한 수정 생성"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행에 대해 댓글 수정 성공 후, success(String) 리턴"
    )
    @LogTrace
    @PutMapping("/{tripId}/reply/{replyId}")
    public String updateTripReply(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }


    @Operation(
            summary = "여행에 대한 댓글 삭제 API",
            description = "여행에 대한 삭제 생성"
    )
    @ApiResponse(
            responseCode = "200",
            description = "여행에 대해 댓글 삭제 성공 후, success(String) 리턴"
    )
    @LogTrace
    @DeleteMapping("/{tripId}/reply/{replyId}")
    public String deleteTripReply(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }



}
