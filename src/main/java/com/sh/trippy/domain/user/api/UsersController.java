package com.sh.trippy.domain.user.api;

import com.sh.trippy.domain.user.api.dto.UserInfoResDto;
import com.sh.trippy.domain.user.api.dto.UserInfoUpdateReqDto;
import com.sh.trippy.domain.user.application.UsersService;
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
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "User Mypage", description = "회원 마이페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/mypage")
public class UsersController {

    private final UsersService usersService;

    /**
     * 기존 유저인지 확인
     * 로그인, 회원가입 > 기존 유저 확인 > 마이페이지 설정(NO) or home화면(YES)
     * @param userInfoFromHeaderDto
     * @return
     */
    @Operation(
            summary = "로그인/회원가입 후 기존 유저 확인 API",
            description = "로그인/회원가입 후 기존 유저 확인"
    )
    @ApiResponse(
            responseCode = "200",
            description = "기존 유저인지 확인하고, 기존 유저면 1, 신규 유저면 0 리턴"
    )
    @LogTrace
    @GetMapping("/check")
    public ResponseEntity<Integer> checkExistUser(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){

        UserInfoResDto userInfo = usersService.getUserInfo(userInfoFromHeaderDto.getUserId());

        return new ResponseEntity<>(0, HttpStatus.OK);
    }

    @Operation(
            summary = "마이페이지 회원 정보 조회 API",
            description = "마이페이지에서 회원 정보 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "마이페이지 회원 정보 조회에 성공 후, 유저 정보 리턴"
    )
    @LogTrace
    @GetMapping("")
    public ResponseEntity<UserInfoResDto> getMypageProfile(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){

        UserInfoResDto userInfo = usersService.getUserInfo(userInfoFromHeaderDto.getUserId());

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @Operation(
            summary = "마이페이지 프로필 수정 API",
            description = "마이페이지에서 프로필 수정"
    )
    @ApiResponse(
            responseCode = "200",
            description = "마이페이지 프로필 수정에 성공 후, success(String) 리턴"
    )
    @LogTrace
    @PutMapping("")
    public String updateMypageProfile(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){

        UserInfoResDto userInfo = usersService.getUserInfo(userInfoFromHeaderDto.getUserId());

        return "success";
    }


    @Operation(
            summary = "마이페이지 계정 탈퇴 API",
            description = "마이페이지에서 계정 탈퇴"
    )
    @ApiResponse(
            responseCode = "200",
            description = "마이페이지 계정 탈퇴 후, success(String) 리턴"
    )
    @LogTrace
    @DeleteMapping("")
    public String cancelAccount(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){

        UserInfoResDto userInfo = usersService.getUserInfo(userInfoFromHeaderDto.getUserId());

        return "success";
    }


    @Operation(
            summary = "마이페이지 유료버전 구매 API",
            description = "마이페이지에서 유료버전 구매"
    )
    @ApiResponse(
            responseCode = "200",
            description = "유료버전 구매 후, success(String) 리턴"
    )
    @LogTrace
    @PatchMapping("/paidversion")
    public String purchasePaidVersion(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){

        UserInfoResDto userInfo = usersService.getUserInfo(userInfoFromHeaderDto.getUserId());

        return "success";
    }



//    /**
//     * 유저 유무 확인
//     */
//    @Operation(
//            summary = "유저가 존재하는지 확인하는 API",
//            description = "닉네임을 통해 유저 존재 유무 확인 "
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "존재 시 1, 없을 시 0 반환"
//    )
//    @LogTrace
//    @GetMapping("/check")
//    public ResponseEntity<Integer> updateUserInfo(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){
//
//        int userExist = usersService.isUserExist(userInfoFromHeaderDto);
//
//        return new ResponseEntity<>(userExist, HttpStatus.OK);
//    }




//    /**
//     * 카카오 최초 로그인 후 & 사용자 정보 수정
//     */
//    @Operation(
//            summary = "최초 로그인 후 & MyPage사용자 정보 수정 API",
//            description = "최초 로그인 후 & MyPage사용자 정보 수정"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "유저 정보 수정에 성공하였습니다."
//    )
//    @LogTrace
//    @PutMapping("")
//    public ResponseEntity<String> updateUserInfo(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto,
//                                                 @RequestPart(value = "img", required = false) MultipartFile file,
//                                                 @RequestPart(value = "userInfo") UserInfoUpdateReqDto userInfoUpdateReqDto){
//
//        usersService.updateUserInfo(userInfoFromHeaderDto.getUserId(), userInfoUpdateReqDto, file);
//
//        return new ResponseEntity<>("success", HttpStatus.OK);
//    }






}
