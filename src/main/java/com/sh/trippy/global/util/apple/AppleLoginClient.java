package com.sh.trippy.global.util.apple;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.sh.trippy.api.login.apple.controller.dto.AppleAuthTokenInfoResDto;
import com.sh.trippy.api.login.apple.controller.dto.AppleResponseInfoResDto;
import com.sh.trippy.global.log.LogTrace;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.Security;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppleLoginClient {

    private final RestTemplate restTemplate;
    private static final String TOKEN_BASE_URL = "https://appleid.apple.com";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String PROVIDER = "apple";

    @Value("${apple.team-id}")
    private String teamId;
    @Value("${apple.key-id}")
    private String keyId;
    @Value("${apple.client-id}")
    private String clientId;
    @Value("${apple.private-key}")
    private String privatKey;

    /**
     * Apple 토큰 인증 API 호출 -> 응답받은 ID 토큰을 JWT Decoding 처리 -> AppleUserInfoResponseDto로 반환
     * @param authorizationCode front로부터 받은 인증 코드
     * @return 디코딩된 사용자 정보를 담고 있는 AppleUserInfoResponseDto 객체
     */
    @LogTrace
    public AppleResponseInfoResDto getAppleUserProfile(String authorizationCode) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(APPLICATION_FORM_URLENCODED_VALUE));

        HttpEntity<String> request = new HttpEntity<>("client_id=" + clientId +
                "&client_secret=" + generateClientSecret() +
                "&grant_type=" + GRANT_TYPE +
                "&code=" + authorizationCode, headers);

        ResponseEntity<AppleAuthTokenInfoResDto> response = restTemplate.exchange(
                TOKEN_BASE_URL + "/auth/token", HttpMethod.POST, request, AppleAuthTokenInfoResDto.class);

        DecodedJWT decodedJWT = com.auth0.jwt.JWT.decode(Objects.requireNonNull(response.getBody()).getIdToken());

        String refreshToken = response.getBody().getRefreshToken();


        log.info("====== refreshToken : " + refreshToken);

        String subject = decodedJWT.getClaim("sub").asString();
        String email = decodedJWT.getClaim("email").asString();

        log.info("===== sub : " + subject);
        log.info("===== email : " + email);

        AppleResponseInfoResDto appleResponseInfoResDto = new AppleResponseInfoResDto(subject, email, refreshToken, PROVIDER);

        return appleResponseInfoResDto;
    }


    /**
     * refesh_token으로 탈퇴하기(revoke)
     *
     * @param refreshToken
     */
    @LogTrace
    public void logoutAppleAccount(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(APPLICATION_FORM_URLENCODED_VALUE));

        HttpEntity<String> request = new HttpEntity<>("client_id=" + clientId +
                "&client_secret=" + generateClientSecret() +
                "&token=" + refreshToken +
                "&token_type_hint=" + "refresh_token", headers);

        // response 없음
        restTemplate.exchange(TOKEN_BASE_URL + "/auth/revoke", HttpMethod.POST, request, String.class);


    }



    /**
     * Apple의 인증 서버와의 통신에 사용될 JWT을 생성하기 위해 사용되는 ClientSecret
     * ClientSecret은 토큰 요청 시 서명 목적으로 사용되며, 공개키/비공개키 인증 메커니즘이 포함됨
     * @return 생성된 JWT ClientSecret
     */
    private String generateClientSecret() {
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(5);

        return Jwts.builder()
                .setHeaderParam(JwsHeader.KEY_ID, keyId) // appleProperties.getKeyId()
                .setIssuer(teamId) // appleProperties.getTeamId()
                .setAudience(TOKEN_BASE_URL)
                .setSubject(clientId)
                .setExpiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
                .setIssuedAt(new Date())
                .signWith(getPrivateKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    /**
     * 애플의 JWT 클라이언트 시크릿 생성을 위한 비공개 키 로드
     * @return 로드된 RSA 비공개 키
     */
    private PrivateKey getPrivateKey() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privatKey); //appleProperties.getPrivateKey()
            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(privateKeyBytes);

            return converter.getPrivateKey(privateKeyInfo);
        } catch (Exception e) {
            throw new RuntimeException("Error converting private key from String", e);
        }
    }



}
