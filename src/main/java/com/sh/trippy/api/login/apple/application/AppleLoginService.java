package com.sh.trippy.api.login.apple.application;

import com.sh.trippy.api.login.apple.controller.dto.AppleUserInfoResponseDto;
import com.sh.trippy.global.log.LogTrace;
import com.sh.trippy.global.util.apple.AppleLoginClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppleLoginService {

    private final AppleLoginClient appleLoginClient;

    @LogTrace
    @Transactional
    public AppleUserInfoResponseDto appleLogin(@RequestParam(name = "authorizationCode") String authorizationCode) throws IOException {

        AppleUserInfoResponseDto appleUserProfile = appleLoginClient.getAppleUserProfile(authorizationCode);

        return appleUserProfile;
    }


}
