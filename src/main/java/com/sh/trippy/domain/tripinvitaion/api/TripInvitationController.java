package com.sh.trippy.domain.tripinvitaion.api;


import com.sh.trippy.domain.tripinvitaion.application.TripInvitationService;
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

@Tag(name = "Trip Invitation Controller", description = "Trip Invitation API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip/invitation")
public class TripInvitationController {

    private final TripInvitationService tripInvitationService;

    /**
     * Host가 Guest 초대하기
     */
    @Operation(
            summary = "Host가 Guest 초대하기 API",
            description = "Host가 Guest 초대하기"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Host가 Guest 초대하기 후 success 리턴"
    )
    @LogTrace
    @PostMapping("/{tripId}")
    public ResponseEntity<String> inviteGuest(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto,
                                      @PathVariable(name = "tripId") Long tripId,
                                      @RequestParam(name = "nickname") String nickname) {

        tripInvitationService.inviteGuest(userInfoFromHeaderDto, tripId, nickname);

        // firebase로 GUEST한테 알림 보내기

        return new ResponseEntity<>("success", HttpStatus.OK);
    }




    /**
     * Host가 Guest 초대 취소하기
     */
    @Operation(
            summary = "Host가 Guest 초대하기 API",
            description = "Host가 Guest 초대하기"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Host가 Guest 초대하기 후 success 리턴"
    )
    @LogTrace
    @PostMapping("/{invitation}")
    public ResponseEntity<String> deleteInvitation(@PathVariable(name = "invitation") Long invitationId) {

        tripInvitationService.deleteInvitation(invitationId);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }









    /**
     * Geust가 Host의 초대 거절하기
     */




}
