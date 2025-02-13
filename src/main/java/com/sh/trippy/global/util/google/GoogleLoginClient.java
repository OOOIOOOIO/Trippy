package com.sh.trippy.global.util.google;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.trippy.api.login.google.controller.dto.GoogleInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleLoginClient {

    private final RestTemplate restTemplate;
    private static final String TOKEN_URL = "https://oauth2.googleapis.com/tokeninfo";


    /**
     * idToken으로 유저 검증
     * @param idToken
     * @return
     * @throws ParseException
     * @throws JsonProcessingException
     */
    public String getGoogleUserInfo(String idToken) throws ParseException, JsonProcessingException {

        log.info("====== idToken : " + idToken);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("id_token", idToken);

        ResponseEntity<GoogleInfoResponse> response = restTemplate.postForEntity(TOKEN_URL, parameters, GoogleInfoResponse.class);

        log.info("======= idToken 검증 response : " + response.getBody());

        String email = response.getBody().getEmail();

        log.info("===== email = " + email);

        return email;
    }

}
