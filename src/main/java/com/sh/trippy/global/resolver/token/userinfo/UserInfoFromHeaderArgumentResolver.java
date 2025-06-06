package com.sh.trippy.global.resolver.token.userinfo;

import com.sh.trippy.global.jwt.JwtClaimDto;
import com.sh.trippy.global.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Slf4j
@RequiredArgsConstructor
@Component
public class UserInfoFromHeaderArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtils jwtUtils;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserInfoFromHeaderDto.class);
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("========= UserInfoFromHeaderArgumentResolver ==========");

        String accessToken = jwtUtils.getAccessTokenFromHeader((HttpServletRequest) webRequest.getNativeRequest());

        JwtClaimDto claimFromAccessToken = jwtUtils.getClaimFromToken(accessToken);


        return new UserInfoFromHeaderDto(claimFromAccessToken.getUserId(), claimFromAccessToken.getEmail(), claimFromAccessToken.getProvider(), claimFromAccessToken.isPaidFlag());


    }
}
