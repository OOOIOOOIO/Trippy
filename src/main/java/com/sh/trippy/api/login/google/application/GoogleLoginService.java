package com.sh.trippy.api.login.google.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sh.trippy.domain.user.application.UsersService;
import com.sh.trippy.global.util.google.GoogleLoginClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GoogleLoginService {

    private final GoogleLoginClient googleLoginClient;
    private final UsersService usersService;
    private static final String PROVIDER = "google";

    public int googleLogin(String accessToken) throws ParseException, JsonProcessingException {

        String email = googleLoginClient.getGoogleUserInfo(accessToken);

        return usersService.saveGoogleUser(email, PROVIDER);

    }


}
