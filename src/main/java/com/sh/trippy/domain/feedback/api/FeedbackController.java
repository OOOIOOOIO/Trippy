package com.sh.trippy.domain.feedback.api;

import com.sh.trippy.global.log.LogTrace;
import com.sh.trippy.global.resolver.token.userinfo.UserInfoFromHeader;
import com.sh.trippy.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Trip User Feedback Controller", description = "Trip User Feedback API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Operation(
            summary = "Trippy 어플에 대한 피드백 생성 API",
            description = "유저가 Trippy 어플에 대한 피드백 생성"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Trippy 어플에 대한 피드백 생성 성공 후, success(String) 리턴"
    )
    @LogTrace
    @PostMapping("/{tripId}/checklist")
    public String createTrippyFeedback(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){


        return "success";
    }


}
