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
     * Host가 Guest 초대 취소하기 or Geust가 Host의 초대 거절하기
     */
    @Operation(
            summary = "Host가 Guest 초대 취소하기 or Geust가 Host의 초대 거절하기 API",
            description = "Host가 Guest 초대 취소하기 or Geust가 Host의 초대 거절하기"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Host가 Guest 초대 취소 or Geust가 Host의 초대 거절하기 후 success 리턴"
    )
    @LogTrace
    @DeleteMapping("/{invitation}")
    public ResponseEntity<String> deleteInvitation(@PathVariable(name = "invitation") Long invitationId) {

        tripInvitationService.deleteInvitation(invitationId);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }



    /**
     * Host로써 초대한 내역 보기
     */
    @Operation(
            summary = "Host로써 초대한 내역 보기 API",
            description = "Host로써 초대한 내역 보기"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Host로써 초대한 내역 조회 후 초대내역 List로 리턴"
    )
    @LogTrace
    @GetMapping("/sending")
    public ResponseEntity<String> getSendInvitationList(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto) {

        tripInvitationService.getSendInvitationList(userInfoFromHeaderDto);

        // firebase로 GUEST한테 알림 보내기

        return new ResponseEntity<>("success", HttpStatus.OK);
    }



    /**
     * GUEST로써 초대받은 내역 보기
     */
    @Operation(
            summary = "GUEST로써 초대받은 내역 보기 API",
            description = "GUEST로써 초대받은 내역 보기"
    )
    @ApiResponse(
            responseCode = "200",
            description = "GUEST로써 초대받은 내역 조회 후 초대내역 List로 리턴"
    )
    @LogTrace
    @GetMapping("/receiving")
    public ResponseEntity<String> getReceiveInvitationList(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto) {

        tripInvitationService.getReceiveInvitationList(userInfoFromHeaderDto);

        // firebase로 GUEST한테 알림 보내기

        return new ResponseEntity<>("success", HttpStatus.OK);
    }




}
