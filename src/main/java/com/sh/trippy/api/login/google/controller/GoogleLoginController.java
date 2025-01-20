package com.sh.trippy.api.login.google.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sh.trippy.api.login.google.application.GoogleLoginService;
import com.sh.trippy.global.log.LogTrace;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Google Login API", description = "구글 로그인 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/google")
public class GoogleLoginController {

    private final GoogleLoginService googleLoginService;


    @Operation(
            summary = "구글 계정 회원가입/로그인 API",
            description = "fornt에서 idToken 받아서 구글 계정으로 회원가입/로그인하기"
    )
    @ApiResponse(
            responseCode = "200",
            description = "구글 계정 로그인 성공 후 유저가 이미 존재한다면 1, 신규라면 -1 리턴"
    )
    @LogTrace
    @PostMapping("/login")
    public ResponseEntity<Integer> googleLogin(@RequestParam(name = "idToken") String idToken) throws ParseException, JsonProcessingException {

        int isUserExist = googleLoginService.googleLogin(idToken);

        return new ResponseEntity<>(isUserExist, HttpStatus.OK);
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
    public ResponseEntity<String> googleLogout() {

        return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
