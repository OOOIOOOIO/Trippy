package com.sh.trippy.api.jwt.controller;

import com.sh.trippy.api.jwt.application.TokenService;
import com.sh.trippy.api.jwt.controller.dto.TokenIssueResDto;
import com.sh.trippy.api.login.UserTokenResDto;
import com.sh.trippy.global.jwt.JwtUtils;
import com.sh.trippy.global.log.LogTrace;
import com.sh.trippy.global.resolver.token.reissue.TokenForReIssueFromHeader;
import com.sh.trippy.global.resolver.token.reissue.TokenForReIssueFromHeaderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Token Controller", description = "Token API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token/reissue")
public class TokenIssueController {

    private final TokenService tokenService;
    private final JwtUtils jwtUtils;

    /**
     * 토큰 정보가 일치하지 않는 경우
     * J002
     * J003
     * J004
     * --> 다시 로그인 해주세요
     */
    @Operation(
            summary = "JWT 토큰 형식이 잘못된 경우",
            description = "토큰 재발급"
    )
    @ApiResponse(
            responseCode = "200",
            description = "에러코드 J001, J002, J003일 떄 accessToken 재발급"
    )
    @LogTrace
    @PostMapping("/")
    public ResponseEntity<TokenIssueResDto> reissueTokens(@TokenForReIssueFromHeader TokenForReIssueFromHeaderDto tokenForReIssueFromHeaderDto) {


        String accessToken = jwtUtils.generateAccessToken(tokenForReIssueFromHeaderDto.getUserId(), tokenForReIssueFromHeaderDto.getEmail(), tokenForReIssueFromHeaderDto.getProvider(), tokenForReIssueFromHeaderDto.isPaidFlag());
        String refreshToken = jwtUtils.generateRefreshToken(tokenForReIssueFromHeaderDto.getUserId(), tokenForReIssueFromHeaderDto.getEmail(), tokenForReIssueFromHeaderDto.getProvider(), tokenForReIssueFromHeaderDto.isPaidFlag());

        tokenService.uploadAccessTokenToRedis(accessToken, tokenForReIssueFromHeaderDto.getUserId()); // redis에 쏘기
        tokenService.uploadRefreshTokenToRedis(refreshToken, tokenForReIssueFromHeaderDto.getUserId());

        return new ResponseEntity<>(new TokenIssueResDto(accessToken, refreshToken, 1), HttpStatus.OK);

    }



    /**
     * accessToken 기간 만료시
     * J005
     * J006
     * --> accessToken 재발급
     */
    @Operation(
            summary = "AccessToken 만료시",
            description = "AccessToken 재발급"
    )
    @ApiResponse(
            responseCode = "200",
            description = "에러코드 J005, J006일 떄 accessToken 재발급"
    )
    @LogTrace
    @PostMapping("/access")
    public ResponseEntity<String> reissueAccessToken(@TokenForReIssueFromHeader TokenForReIssueFromHeaderDto tokenForReIssueFromHeaderDto) {


        String accessToken = jwtUtils.generateAccessToken(tokenForReIssueFromHeaderDto.getUserId(), tokenForReIssueFromHeaderDto.getEmail(), tokenForReIssueFromHeaderDto.getProvider(), tokenForReIssueFromHeaderDto.isPaidFlag());

        tokenService.uploadAccessTokenToRedis(accessToken, tokenForReIssueFromHeaderDto.getUserId()); // redis에 쏘기

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }


    /**
     * refeshToken 기간 만료시
     * J001
     * J007
     *
     */
    @Operation(
            summary = "RefreshToken 만료시",
            description = "RefreshToken 재발급"
    )
    @ApiResponse(
            responseCode = "200",
            description = "에러코드 J001, J007일 떄 accessToken, refreshToken 재발급"
    )
    @LogTrace
    @PostMapping("/refresh")
    public ResponseEntity<TokenIssueResDto> reissueRefreshToken(@TokenForReIssueFromHeader TokenForReIssueFromHeaderDto tokenForReIssueFromHeaderDto){


        String accessToken = jwtUtils.generateAccessToken(tokenForReIssueFromHeaderDto.getUserId(), tokenForReIssueFromHeaderDto.getEmail(), tokenForReIssueFromHeaderDto.getProvider(), tokenForReIssueFromHeaderDto.isPaidFlag());
        String refreshToken = jwtUtils.generateRefreshToken(tokenForReIssueFromHeaderDto.getUserId(), tokenForReIssueFromHeaderDto.getEmail(), tokenForReIssueFromHeaderDto.getProvider(), tokenForReIssueFromHeaderDto.isPaidFlag());

        tokenService.uploadAccessTokenToRedis(accessToken, tokenForReIssueFromHeaderDto.getUserId()); // redis에 쏘기
        tokenService.uploadRefreshTokenToRedis(refreshToken, tokenForReIssueFromHeaderDto.getUserId());

        return new ResponseEntity<>(new TokenIssueResDto(accessToken, refreshToken, 1), HttpStatus.OK);
    }




}
