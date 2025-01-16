package com.sh.trippy.api.login.apple.controller;

import com.sh.trippy.api.login.apple.application.AppleLoginService;
import com.sh.trippy.api.login.apple.controller.dto.AppleUserInfoResponseDto;
import com.sh.trippy.global.log.LogTrace;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "Apple Login API", description = "애플 로그인 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/apple")
public class AppleLoginController {

    private final AppleLoginService appleLoginService;

    @Operation(
            summary = "애플 계정 회원가입/로그인 API",
            description = "fornt에서 코드 받아서 애플 계정으로 회원가입/로그인하기"
    )
    @ApiResponse(
            responseCode = "200",
            description = "애플 계정 로그인 성공 후 success(String) 리턴"
    )
    @LogTrace
    @PostMapping("/login")
    public ResponseEntity<String> appleLogin(@RequestParam(name = "authorizationCode") String authorizationCode) throws IOException {

        Long userId = appleLoginService.appleLogin(authorizationCode);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    @Operation(
            summary = "애플 계정 로그아웃 API",
            description = "애플 계정 로그아웃하기"
    )
    @ApiResponse(
            responseCode = "200",
            description = "애플 계정 로그아웃 성공 후 success(String) 리턴"
    )
    @LogTrace
    @PostMapping("/logout")
    public ResponseEntity<String> appleLogout() {

        return new ResponseEntity<>("success", HttpStatus.OK);
    }








}
