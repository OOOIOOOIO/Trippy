package com.sh.trippy.global.util.google;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleLoginClient {

    private final RestTemplate restTemplate;
    private static final String TOKEN_URL = "https://oauth2.googleapis.com/tokeninfo";


    public String getGoogleUserInfo(String idToken) throws ParseException, JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> request = new HttpEntity<>("id_token=" + idToken, headers);


        ResponseEntity<String> response = restTemplate.exchange(TOKEN_URL, HttpMethod.GET, request, String.class);

        JSONParser parser = new JSONParser();
        JSONObject jsonBody = (JSONObject) parser.parse(response.getBody());

        Map<String, Object> body = new ObjectMapper().readValue(jsonBody.toString(), Map.class);

//        String.valueOf(body.get("name"));
        String email = String.valueOf(body.get("email"));

        return email;
    }

}
