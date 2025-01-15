package com.sh.trippy.api.login.google.controller;

import com.sh.trippy.global.log.LogTrace;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Google Login API", description = "구글 로그인 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/google")
public class GoogleLoginController {

    @Operation(
            summary = "구글 계정 회원가입/로그인 API",
            description = "fornt에서 코드 받아서 구글 계정으로 회원가입/로그인하기"
    )
    @ApiResponse(
            responseCode = "200",
            description = "구글 계정 로그인 성공 후 success(String) 리턴"
    )
    @LogTrace
    @PostMapping("/login")
    public String googleLogin(){

        return "success";
    }


    @Operation(
            summary = "구글 계정 로그아웃 API",
            description = "구글 계정 로그아웃하기"
    )
    @ApiResponse(
            responseCode = "200",
            description = "구글 계정 로그아웃 성공 후 success(String) 리턴"
    )
    @LogTrace
    @PostMapping("/logout")
    public String googleLogout() {

        return "success";
    }


}
